package com.galvanize.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorsServiceTests {

    private AuthorsService authorsService;

    @Mock
    AuthorsRepository authorsRepository;
    @Mock
    BooksRepository booksRepository;

    @BeforeEach
    void setup() {
        authorsService = new AuthorsService(authorsRepository);
    }

    @Test
    void getAuthors() {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1990, books);
        when(authorsRepository.findAll()).thenReturn(Arrays.asList(author));
        AuthorsList authorsList = authorsService.getAuthors();
        assertThat(authorsList).isNotNull();
        assertThat(authorsList.isEmpty()).isFalse();
    }

    @Test
    void getAuthorSearchIdReturnsAuthor() {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1990, books);
        when(authorsRepository.findById(anyLong())).thenReturn(Optional.of(author));
        Author author2 = authorsService.getAuthor(author.getId());
        assertThat(author2).isNotNull();
        assertThat(author2.getName()).isEqualTo("Eli");
    }

    @Test
    void addAuthorReturnsAuthor() {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1990, books);
        when(authorsRepository.save(any(Author.class))).thenReturn(author);
        Author author2 = authorsService.addAuthor(author);
        assertThat(author2).isNotNull();
    }

    @Test
    void updateAuthor() {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1990, books);
        when(authorsRepository.save(any(Author.class))).thenReturn(author);
        Author author2 = authorsService.addAuthor(author);
        assertThat(author2).isNotNull();
        assertThat(author2.getId()).isEqualTo(author.getId());
    }

    @Test
    void deleteAuthor() {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1990, books);
        when(authorsRepository.findById(anyLong())).thenReturn(Optional.of(author));
        authorsService.deleteAuthor(author.getId());
        verify(authorsRepository).delete(any(Author.class));
    }


}
