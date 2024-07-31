package com.galvanize.spring;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String Name;
    private int birthYear;
    @ManyToMany
    @JoinTable(name = "author_books",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    private ArrayList<Book> books;

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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
