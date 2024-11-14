package com.example.books;

import com.example.books.BookController;
import com.example.books.BookRepositories;
import com.example.books.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	@Bean
	public BookRepositories bookRepository() {
		return new BookRepositories();
	}

	@Bean
	public BookService bookService() {
		return new BookService(bookRepository());
	}
}
