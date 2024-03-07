package com.gxlpes.auth.api.testing.book;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookBuilder bookBuilder;

    public BookService(BookRepository repository, BookBuilder bookBuilder) {
        this.repository = repository;
        this.bookBuilder = bookBuilder;
    }

    public void save(BookRequest request) {
        var book = bookBuilder
                .setId(1)
                .setAuthor("Author Name")
                .setIsbn("ISBN12345")
                .setCreateDate(LocalDateTime.now())
                .setLastModified(LocalDateTime.now())
                .setCreatedBy(1)
                .setLastModifiedBy(1)
                .build();
    }

    public List<Book> findAll() {
        return repository.findAll();
    }
}
