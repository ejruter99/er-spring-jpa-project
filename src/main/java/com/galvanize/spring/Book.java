package com.galvanize.spring;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="books")
public class Book {
    @Id @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String title;
    private String ISBN;
    private int publicationYear;
    @ManyToMany
//    @JoinColumn(name = "author_id")
    @JoinTable(name = "author_books",
            joinColumns = @JoinColumn(name = "authors_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id"))
    @Column(name = "author")
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Book() {
    }

    public Book(long id, String title, String ISBN, int publicationYear, ArrayList<Author> authors, Genre genre) {
        this.id = id;
        this.title = title;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.authors = authors;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
