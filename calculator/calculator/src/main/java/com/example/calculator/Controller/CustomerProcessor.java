package com.example.calculator.Controller;

import com.example.calculator.Data.Customer;
import com.example.calculator.Data.CustomerPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class CustomerProcessor {

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Customer> customers = new ArrayList<>();

    @Value("${dataset.service.url}")
    private String datasetServiceUrl;

    @Value("${result.service.url}")
    private String resultServiceUrl;

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            customers.clear();
            String response = restTemplate.getForObject(datasetServiceUrl, String.class);

            JsonNode jsonNode = objectMapper.readTree(response);

            for (JsonNode eventNode : jsonNode.get("events")) {
                Customer customer = new Customer();
                customer.setCustomerId(eventNode.get("customerId").asText());
                customer.setWorkloadId(eventNode.get("workloadId").asText());
                customer.setTimestamp(eventNode.get("timestamp").asLong());
                customer.setEventType(eventNode.get("eventType").asText());
                customers.add(customer);
            }

            customers.sort(Comparator.comparingLong(Customer::getTimestamp));

            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/postCustomerData")
    public ResponseEntity<Map<String, List<CustomerPost>>> postCustomerData() {
        if (customers == null || customers.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("result", Collections.singletonList(new CustomerPost("Error", 0))));
        }

        Map<String, Long> aggregatedConsumption = new HashMap<>();
        List<CustomerPost> results = new ArrayList<>();
        Map<String, Long> startTimestamps = new HashMap<>();

        for (Customer customer : customers) {
            if ("start".equalsIgnoreCase(customer.getEventType())) {
                startTimestamps.put(customer.getWorkloadId(), customer.getTimestamp());
            } else if ("stop".equalsIgnoreCase(customer.getEventType())) {
                Long startTimestamp = startTimestamps.get(customer.getWorkloadId());
                if (startTimestamp != null) {
                    long consumptionValue = calculateConsumption(startTimestamp, customer.getTimestamp());
                    aggregatedConsumption.merge(customer.getCustomerId(), consumptionValue, Long::sum);
                }
            }
        }

        for (Map.Entry<String, Long> entry : aggregatedConsumption.entrySet()) {
            results.add(new CustomerPost(entry.getKey(), entry.getValue()));
        }

        return ResponseEntity.ok(Collections.singletonMap("result", results));
    }

    private long calculateConsumption(Long startTimestamp, Long stopTimestamp) {
        LocalDateTime startDateTime = Instant.ofEpochMilli(startTimestamp)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime stopDateTime = Instant.ofEpochMilli(stopTimestamp)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        return ChronoUnit.SECONDS.between(startDateTime, stopDateTime);
    }
}
