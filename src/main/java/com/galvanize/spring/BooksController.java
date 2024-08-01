package com.galvanize.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class BooksController {

    BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/api/books")
    public ResponseEntity<BooksList> getBooks(@RequestParam(required = false) String author,
                                              @RequestParam(required = false) String genre) {
        BooksList booksList;
        if (author == null && genre == null) {
            booksList = booksService.getBooks();
        } else {
            booksList = booksService.getBooks(author, genre);
        }
        return booksList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(booksList);
    }

    @GetMapping("/api/books/{id}")
    public Book getBookWithVin(@PathVariable Long id) {
        return booksService.getBook(id);
    }

    @PostMapping("/api/books")
    public Book addBook(@RequestBody Book book) {
        return booksService.addBook(book);
    }

    @PatchMapping("/api/books/{id}")
    public Book updateBook(@PathVariable Long id,
                           @RequestBody UpdateBookRequest update) {
        Book book = booksService.updateBook(id, update.getTitle(), update.getISBN());
        book.setTitle(update.getTitle());
        book.setISBN(update.getISBN());
        return book;
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        try {
            booksService.deleteBook(id);
        } catch (BookNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void bookNotFoundExceptionHandler(BookNotFoundException e) {

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void bookNotFoundExceptionHandler(InvalidBookException e) {

    }

}
