package com.example.books;

import com.example.books.Book;
import java.util.Optional;
import java.util.Set;

public interface BookRepository {
    Optional<Book> findBookById(Long id);
    Set<Book> findAll();
}
