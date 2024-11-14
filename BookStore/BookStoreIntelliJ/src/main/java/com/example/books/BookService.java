package com.example.books;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.books.BookData;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<BookData> getBook(long id) {
        return bookRepository.findBookById(id)
                .map(book -> new BookData(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getReviews()));
    }

    public Set<BookData> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookData(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getReviews()))
                .collect(Collectors.toSet());
    }

    public Optional<String> getReviewsAsText(long id) {
        List<String> reviews = ((BookRepositories) bookRepository).getBookReviews(id);
        return Optional.of(String.join(" ", reviews)); // Alle Rezensionen als Text zusammenfügen
    }
}
