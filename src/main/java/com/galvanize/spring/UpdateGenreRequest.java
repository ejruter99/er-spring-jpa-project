package com.galvanize.spring;

import java.util.ArrayList;
import java.util.List;

public class UpdateGenreRequest {

    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public UpdateGenreRequest(List<Book> books) {
        this.books = books;
    }

    public UpdateGenreRequest() {
        this.books = books;
    }
}
