package ru.vergolyas.library.models;

import jakarta.validation.constraints.NotEmpty;

public class Book {

    private int bookId;
    @NotEmpty(message = "имя не должно быть пустым")
    private String name;
    private String author;
    private int year;
    private int ownerId;

    public Book() {
    }

    public Book(int bookId, String name, String author, int year, int ownerId) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.year = year;
        this.ownerId = ownerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
