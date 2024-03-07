package com.gxlpes.auth.api.testing.book;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookBuilder {

    private Integer id;
    private String author;
    private String isbn;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    private Integer createdBy;
    private Integer lastModifiedBy;

    public BookBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public BookBuilder setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public BookBuilder setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public BookBuilder setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public Book build() {
        Book book = new Book();
        book.setId(this.id);
        book.setAuthor(this.author);
        book.setIsbn(this.isbn);
        book.setCreateDate(this.createDate);
        book.setLastModified(this.lastModified);
        book.setCreatedBy(this.createdBy);
        book.setLastModifiedBy(this.lastModifiedBy);
        return book;
    }
}
