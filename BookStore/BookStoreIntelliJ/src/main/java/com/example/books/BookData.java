package com.example.books;

import java.util.List;

public class BookData {
    private Long id;
    private String title;
    private String author;
    private String description;
    private List<String> reviews;

    public BookData(Long id, String title, String author, String description, List<String> reviews) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.reviews = reviews;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public List<String> getReviews() { return reviews; }
}