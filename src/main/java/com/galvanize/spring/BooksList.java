package com.galvanize.spring;

import java.util.List;
import java.util.Objects;

public class BooksList {

    private List<Book> books;

    public BooksList(List<Book> books) {
        this.books = books;
    }

    public BooksList() {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean isEmpty() {
        return this.books.isEmpty();
    }

    @Override
    public String toString() {
        return "BooksList{" +
                "books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksList booksList = (BooksList) o;
        return Objects.equals(books, booksList.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books);
    }
}
