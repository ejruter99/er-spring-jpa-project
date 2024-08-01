package com.galvanize.spring;

import java.util.List;
import java.util.Objects;

public class AuthorsList {

    private List<Author> authors;

    public AuthorsList(List<Author> authors) {
        this.authors = authors;
    }

    public AuthorsList() {
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public boolean isEmpty() {
        return this.authors.isEmpty();
    }

    @Override
    public String toString() {
        return "AuthorsList{" +
                "authors=" + authors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorsList authorsList = (AuthorsList) o;
        return Objects.equals(authors, authorsList.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authors);
    }
}
