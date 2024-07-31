package com.galvanize.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.LocalTime.now;
import static org.mockito.Mockito.when;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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


}
