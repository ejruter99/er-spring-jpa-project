package com.galvanize.spring;

import java.util.List;

public class UpdateAuthorRequest {

    private int birthYear;
    private List<Book> books;


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public UpdateAuthorRequest(int birthYear, List<Book> books) {
        this.birthYear = birthYear;
        this.books = books;
    }
}
