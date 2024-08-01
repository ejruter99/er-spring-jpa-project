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

import static org.mockito.Mockito.when;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AuthorsControllerTests {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    BooksService booksService;
    @MockBean
    AuthorsService authorsService;
    @MockBean
    GenreService genreService;

    @Test
    void getAuthorsReturnsAllAuthors() throws Exception {
        ArrayList<Author> authors = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            authors.add(new Author(1+i, "Eli", 1999, books));
        }
        when(authorsService.getAuthors()).thenReturn(new AuthorsList(authors));
        mockMvc.perform(get("/api/authors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authors", hasSize(5)));
    }

    @Test
    void getAuthorReturnsNoContent() throws Exception {
        ArrayList<Author> authors = new ArrayList<>();
        when(authorsService.getAuthors()).thenReturn(new AuthorsList(authors));
        mockMvc.perform(get("/api/authors"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getAuthorsSearchParamBirthYearReturnsAuthor() throws Exception {
        ArrayList<Author> authors = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            authors.add(new Author(1+i, "Eli", 1999, books));
        }
        when(authorsService.getAuthors(anyInt())).thenReturn(new AuthorsList(authors));
        mockMvc.perform(get("/api/authors?birthYear=1999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authors", hasSize(5)));
    }

    @Test
    void getAuthorUsingIdReturnsAuthor() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1999, books);
        when(authorsService.getAuthor(anyLong())).thenReturn(author);
        mockMvc.perform(get("/api/authors/" + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(author.getId()));
    }

    @Test
    void getAuthorUsingIdReturnsNoAuthor() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1999, books);
        when(authorsService.getAuthor(anyLong())).thenThrow(AuthorNotFoundException.class);
        mockMvc.perform(get("/api/authors/" + author.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addAuthorValidReturnsAuthor() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1999, books);
        when(authorsService.addAuthor(any(Author.class))).thenReturn(author);
        mockMvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(author)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Eli"));
    }

    @Test
    void addAuthorReturnsFail() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1999, books);
        when(authorsService.addAuthor(any(Author.class))).thenThrow(InvalidAuthorException.class);
        mockMvc.perform(post("/api/authors").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(author)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAuthorWithObjectReturnsAuthor() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1999, books);
        UpdateAuthorRequest update = new UpdateAuthorRequest(2000, books);
        when(authorsService.updateAuthor(anyLong(), anyInt(), anyList())).thenReturn(author);
        mockMvc.perform(patch("/api/authors/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("birthYear").value(2000));
    }

    @Test
    void updateAuthorWithObjectReturnsBadRequest() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        Author author = new Author(1, "Eli", 1999, books);
        UpdateAuthorRequest update = new UpdateAuthorRequest(2000, books);
        when(authorsService.updateAuthor(anyLong(), anyInt(), anyList())).thenThrow(AuthorNotFoundException.class);
        mockMvc.perform(patch("/api/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(update)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAuthorWithIdExistsWorks() throws Exception {
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isAccepted());
        verify(authorsService).deleteAuthor(anyLong());
    }

    @Test
    void deleteAuthorWithIdNotFound() throws Exception {
        doThrow(new AuthorNotFoundException()).when(authorsService).deleteAuthor(anyLong());
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }



















}
