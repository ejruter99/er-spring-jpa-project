package com.galvanize.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GenreControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BooksService booksService;
    @MockBean
    AuthorsService authorsService;
    @MockBean
    GenreService genreService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getGenresReturnsAllGenres() throws Exception {
        ArrayList<Genre> genres = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            genres.add(new Genre(1+i, "Fantasy", books));
        }
        when(genreService.getGenres()).thenReturn(new GenresList(genres));
        mockMvc.perform(get("/api/genres"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres", hasSize(5)));
    }

    @Test
    void getGenresReturnsNoContent() throws Exception {
        ArrayList<Genre> genres = new ArrayList<>();
        when(genreService.getGenres()).thenReturn(new GenresList(genres));
        mockMvc.perform(get("/api/genres"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getGenreUsingIdReturnsGenre() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreService.getGenre(anyLong())).thenReturn(genre);
        mockMvc.perform(get("/api/genres/" + genre.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(genre.getId()));
    }

    @Test
    void getGenreUsingIdReturnsNoGenre() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreService.getGenre(anyLong())).thenThrow(GenreNotFoundException.class);
        mockMvc.perform(get("/api/genres/" + genre.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addGenreValidReturnsGenre() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreService.addGenre(any(Genre.class))).thenReturn(genre);
        mockMvc.perform(post("/api/genres").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(genre)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Comedy"));
    }

    @Test
    void addGenreReturnsFail() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        when(genreService.addGenre(any(Genre.class))).thenThrow(InvalidGenreException.class);
        mockMvc.perform(post("/api/genres").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(genre)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateGenreWithObjectReturnsGenre() throws Exception {
        List<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        UpdateGenreRequest update = new UpdateGenreRequest(books);
        when(genreService.updateGenre(anyLong(), anyList())).thenReturn(genre);
        mockMvc.perform(patch("/api/genres/" + genre.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Comedy"));
    }

    @Test
    void updateGenreWithObjectReturnsBadRequest() throws Exception {
        List<Book> books = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        UpdateGenreRequest update = new UpdateGenreRequest(books);
        when(genreService.updateGenre(anyLong(), anyList())).thenThrow(InvalidGenreException.class);
        mockMvc.perform(patch("/api/genres/" + genre.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteGenreWithIdExistsWorks() throws Exception {
        mockMvc.perform(delete("/api/genres/1"))
                .andExpect(status().isAccepted());
        verify(genreService).deleteGenre(anyLong());
    }

    @Test
    void deleteGenreWithIdNotFound() throws Exception {
        doThrow(new GenreNotFoundException()).when(genreService).deleteGenre(anyLong());
        mockMvc.perform(delete("/api/genres/1"))
                .andExpect(status().isNoContent());
    }





}
