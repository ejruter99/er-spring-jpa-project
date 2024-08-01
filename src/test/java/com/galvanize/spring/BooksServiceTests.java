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
public class BooksServiceTests {

    private BooksService booksService;

    @Mock
    BooksRepository booksRepository;

    @BeforeEach
    void setup() {
        booksService = new BooksService(booksRepository);
    }

    @Test
    void getBooks() {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksRepository.findAll()).thenReturn(Arrays.asList(book));
        BooksList booksList = booksService.getBooks();
        assertThat(booksList).isNotNull();
        assertThat(booksList.isEmpty()).isFalse();
    }

    @Test
    void getBooksSearchReturnsList() {
        ArrayList<Book> book2 = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", book2));
        when(booksRepository.findByGenreContainsAndAuthorContains(anyString(), anyString()))
                .thenReturn(Arrays.asList(book));
        BooksList booksList = booksService.getBooks("Jeff Kinney", "Comedy");
        assertThat(booksList).isNotNull();
        assertThat(booksList.isEmpty()).isFalse();
    }

    @Test
    void addBookSuccessReturnsBook() {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", books));
        when(booksRepository.save(any(Book.class))).thenReturn(book);
        Book book2 = booksService.addBook(book);
        assertThat(book2).isNotNull();
        assertThat(book2.getTitle()).isEqualTo("Diary of a Wimpy Kid");

    }

    @Test
    void getBookWithIdReturnsBook() {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", books));
        when(booksRepository.findById(anyLong())).thenReturn(Optional.of(book));
        Book book2 = booksService.getBook(book.getId());
        assertThat(book2).isNotNull();
        assertThat(book2.getTitle()).isEqualTo("Diary of a Wimpy Kid");
    }

    @Test
    void updateBook() {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", books));
        when(booksRepository.findById(anyLong()))
                .thenReturn(Optional.of(book));
        when(booksRepository.save(any(Book.class)))
                .thenReturn(book);
        Book book2 = booksService.updateBook(book.getId(), "Diary of a Happy Kid", "54321acd");
        assertThat(book).isNotNull();
        assertThat(book2.getId()).isEqualTo(book.getId());
    }

    @Test
    void deleteBook() {
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>();
        Book book = new Book(1, "Diary of a Wimpy Kid", "123ABC", 2005, authors, new Genre(1, "Comedy", books));
        when(booksRepository.findById(anyLong())).thenReturn(Optional.of(book));
        booksService.deleteBook(book.getId());
        verify(booksRepository).delete(any(Book.class));
    }


}
