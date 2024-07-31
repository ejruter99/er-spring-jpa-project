package com.galvanize.spring;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class BooksController {

    BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/api/books")
    public ResponseEntity<BooksList> getBooks() {
        BooksList booksList = booksService.getBooks();
        return booksList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(booksList);
    }

}
