package com.example.books;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public Set<BookData> listBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/details/{id}")
    public Optional<BookData> fetchBook(@PathVariable long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/reviews/{id}")
    public Optional<String> fetchBookReviews(@PathVariable long id) {
        return bookService.getReviewsAsText(id);
    }
}