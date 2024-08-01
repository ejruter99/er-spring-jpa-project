package com.galvanize.spring;

import java.util.List;
import java.util.Objects;

public class GenresList {

    private List<Genre> genres;

    public GenresList(List<Genre> genres) {
        this.genres = genres;
    }

    public GenresList() {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public boolean isEmpty() {
        return this.genres.isEmpty();
    }

    @Override
    public String toString() {
        return "GenresList{" +
                "genres=" + genres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenresList genresList = (GenresList) o;
        return Objects.equals(genres, genresList.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genres);
    }
}
