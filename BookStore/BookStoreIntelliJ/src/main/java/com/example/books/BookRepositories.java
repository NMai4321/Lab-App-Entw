package com.example.books;

import java.util.*;

public class BookRepositories implements BookRepository {
    private final Set<Book> books = new HashSet<>();
    private final Map<Long, List<String>> bookReviews = new HashMap<>();

    public BookRepositories() {
        // Beispielbücher
        books.add(new Book(1L, "Der Herr der Ringe", "J.R.R. Tolkien", "Ein Buch über einen Hobbit"));
        books.add(new Book(2L, "1984", "George Orwell", "Ein Sci-Fiction Buch über die Zukunft"));
        books.add(new Book(3L, "Der Alchemist", "Paulo Coelho", "Buch über einen Alchimisten"));
        books.add(new Book(4L, "Der Kleine Prinz", "Antoine de Saint-Exupéry", "Kleiner Prinz verirrt sich beim erkunden"));
        books.add(new Book(5L, "Moby-Dick", "Herman Melville", "Buch über die See"));
        books.add(new Book(6L, "Die Chroniken von Narnia", "C.S. Lewis", "Fantasy Buch über einen Löwen"));
        books.add(new Book(7L, "Eragon", "Christopher Paolini", "Fantasy Welt mit Drachen und Magie"));
        books.add(new Book(8L, "Das Lied von Eis und Feuer", "George R.R. Martin", "Verrat, Sex, Religion..."));
        books.add(new Book(9L, "Der Name des Windes", "Patrick Rothfuss", "Die Geschichte eines Barbesitzers"));

        // Rezensionen als Textbeispiel
        bookReviews.put(1L, Arrays.asList(
                "Amazing fantasy adventure!",
                "Ein episches Meisterwerk!"
        ));
        bookReviews.put(2L, Arrays.asList(
                "Ein Klassiker, der zum Nachdenken anregt.",
                "Düster und packend."
        ));
        bookReviews.put(3L, Arrays.asList(
                "Magie und Philosophy. Perfekt!",
                "Super Spannend."
        ));
        bookReviews.put(4L, Arrays.asList(
                "Inspiring and life-changing.",
                "Eine wahre Reise der Selbstentdeckung."
        ));
        bookReviews.put(5L, Arrays.asList(
                "Wirklich erstaunlich!",
                "Eine unglaublich fesselnde Geschichte"
        ));
        bookReviews.put(6L, Arrays.asList(
                "Wie schnell Kinder erwachsen werden...",
                "Mir gab es da zu wenig Magie!"
        ));
        bookReviews.put(7L, Arrays.asList(
                "Ich liebe Drachen.",
                "Es ist soo lange her."
        ));
        bookReviews.put(8L, Arrays.asList(
                "Ich weiß nie, wer wen als nächstes Verrät.",
                "So stelle ich mir das Mittelalter vor."
        ));
        bookReviews.put(9L, Arrays.asList(
                "Wirklich Spannend.",
                "Schade das es nicht weiter geht."
        ));
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();
        book.ifPresent(b -> b.setReviews(getBookReviews(id)));  // Rezensionen setzen
        return book;
    }

    @Override
    public Set<Book> findAll() {
        books.forEach(book -> book.setReviews(getBookReviews(book.getId())));
        return books;
    }

    public List<String> getBookReviews(Long id) {
        return bookReviews.getOrDefault(id, Collections.emptyList());
    }
}
