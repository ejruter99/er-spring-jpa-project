package com.galvanize.spring;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String Name;
    @OneToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private ArrayList<Book> books;

    public Genre(long id, String name, ArrayList<Book> books) {
        this.id = id;
        Name = name;
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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
