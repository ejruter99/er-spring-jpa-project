package com.galvanize.spring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String Name;

    private int birthYear;

//    @JoinTable(name = "author_books",
//            joinColumns = @JoinColumn(name = "authors_id"),
//            inverseJoinColumns = @JoinColumn(name = "books_id"))
    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author() {
    }

    public Author(long id, String name, int birthYear, ArrayList<Book> books) {
        this.id = id;
        Name = name;
        this.birthYear = birthYear;
        this.books = books;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

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
}
