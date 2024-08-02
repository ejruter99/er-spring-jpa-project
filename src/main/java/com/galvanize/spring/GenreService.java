package com.galvanize.spring;

import java.util.List;
import java.util.Optional;

public class GenreService {
    GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public GenresList getGenres() {
        return new GenresList(genreRepository.findAll());
    }

    public Genre getGenre(long id) {
        return genreRepository.findById(id).orElse(null);
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(long id, List<Book> books) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            genre.get().setBooks(books);
            return genreRepository.save(genre.get());
        }
        return null;
    }

    public void deleteGenre(long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            genreRepository.delete(genre.get());
        } else {
            throw new BookNotFoundException();
        }
    }
}
