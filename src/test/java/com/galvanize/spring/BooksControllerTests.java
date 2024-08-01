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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BooksControllerTests {

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
    void getBooksReturnsAllBooks() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(new Author(1, "Jeff Kinney", 1980, book2));
        for (int i = 0; i < 5; i++) {
            books.add(new Book(1+i, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2)));
        }
        when(booksService.getBooks()).thenReturn(new BooksList(books));
        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(5)));
    }

    @Test
    void getBooksReturnsNoContent() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        when(booksService.getBooks()).thenReturn(new BooksList(books));
        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getBooksSearchParamsGenreAndAuthorReturnsBooksList() throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Genre genre = new Genre(1, "Comedy", books);
        authors.add(new Author(1, "Jeff Kinney", 1980, book2));
        for (int i = 0; i < 5; i++) {
            books.add(new Book(1+i, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2)));
        }
        when(booksService.getBooks(anyString(), anyString())).thenReturn(new BooksList(books));
        mockMvc.perform(get("/api/books?author=[Jeff Kinney]&genre=Genre"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.books", hasSize(5))));
    }

    @Test
    void getBooksUsingIdReturnsBook() throws Exception {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksService.getBook(anyLong())).thenReturn(book);
        mockMvc.perform(get("/api/books/" + book.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(book.getId()));
    }

    @Test
    void getBooksUsingIdReturnsNoBook() throws Exception {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksService.getBook(anyLong())).thenThrow(BookNotFoundException.class);
        mockMvc.perform(get("/api/books/" + book.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addBookValidReturnsBook() throws Exception {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksService.addBook(any(Book.class))).thenReturn(book);
        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("Diary of a Wimpy Kid"));
    }

    @Test
    void addBookReturnsFail() throws Exception {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksService.addBook(any(Book.class))).thenThrow(InvalidBookException.class);
        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBookWithObjectReturnsBook() throws Exception {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksService.updateBook(anyLong(), anyString(), anyString())).thenReturn(book);
        mockMvc.perform(patch("/api/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Eli's Book\", \"isbn\":\"1236sdh\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("Eli's Book"))
                .andExpect(jsonPath("isbn").value("1236sdh"));
    }

    @Test
    void updateBookWithObjectReturnsBadRequest() throws Exception {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksService.updateBook(anyLong(), anyString(), anyString())).thenThrow(BookNotFoundException.class);
        mockMvc.perform(patch("/api/books/"+book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Eli's Book\", \"isbn\":\"1236sdh\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBookWithIdExistsWorks() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isAccepted());
        verify(booksService).deleteBook(anyLong());
    }

    @Test
    void deleteBookWithIdNotFound() throws Exception {
        doThrow(new BookNotFoundException()).when(booksService).deleteBook(anyLong());
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

}
