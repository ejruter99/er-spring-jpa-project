package com.galvanize.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenresServiceTests {

    private GenreService genreService;

    @Mock
    GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        genreService = new GenreService(genreRepository);
    }

    @Test
    void getGenress() {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre));
        GenresList genresList = genreService.getGenres();
        assertThat(genresList).isNotNull();
        assertThat(genresList.isEmpty()).isFalse();
    }

    @Test
    void addGenreSuccessReturnsGenre() {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);
        Genre genre2 = genreService.addGenre(genre);
        assertThat(genre2).isNotNull();
        assertThat(genre2.getName()).isEqualTo("Comedy");

    }

    @Test
    void updateGenre() {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreRepository.save(any(Genre.class)))
                .thenReturn(genre);
        Genre genre2 = genreService.addGenre(genre);
        assertThat(genre2).isNotNull();
        assertThat(genre2.getName()).isEqualTo(genre.getName());
    }

    @Test
    void getGenreWithIdReturnsGenre() {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        Genre genre2 = genreService.getGenre(genre.getId());
        assertThat(genre2).isNotNull();
        assertThat(genre2.getName()).isEqualTo("Comedy");
    }

    @Test
    void deleteGenre() {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        genreService.deleteGenre(genre.getId());
        verify(genreRepository).delete(any(Genre.class));
    }

}
