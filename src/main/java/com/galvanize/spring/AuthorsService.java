package com.galvanize.spring;

import java.util.List;
import java.util.Optional;

public class AuthorsService {
    AuthorsRepository authorsRepository;

    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public AuthorsList getAuthors() {
        return new AuthorsList(authorsRepository.findAll());
    }
    public AuthorsList getAuthors(Integer birthYear) {
        return null;
    }

    public Author getAuthor(Long id) {
        return authorsRepository.findById(id).orElse(null);
    }

    public Author addAuthor(Author author) {
        return authorsRepository.save(author);
    }

    public Author updateAuthor(Long id, int birthYear, List<Book> books) {
        Optional<Author> author = authorsRepository.findById(id);
        if (author.isPresent()) {
            author.get().setBirthYear(birthYear);
            author.get().setBooks(books);
            return authorsRepository.save(author.get());
        }
        return null;
    }

    public void deleteAuthor(long id) {
        Optional<Author> author = authorsRepository.findById(id);
        if (author.isPresent()) {
            authorsRepository.delete(author.get());
        } else {
            throw new AuthorNotFoundException();
        }
    }
}
