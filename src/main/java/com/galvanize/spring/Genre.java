package com.galvanize.spring;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "genre_id")
    private long id;
    private String Name;
    @OneToMany(mappedBy = "genre")
    private List<Book> books;

    public Genre() {
    }

    public Genre(long id, String name, List<Book> books) {
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
