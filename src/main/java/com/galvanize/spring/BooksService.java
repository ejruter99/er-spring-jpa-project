package com.galvanize.spring;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BooksService {
    BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public BooksList getBooks() {
        return new BooksList(booksRepository.findAll());
    }

    public BooksList getBooks(Author author, Genre genre) {
        return new BooksList(booksRepository.findByGenreAndAuthors(author, genre));
    }

    public Book getBook(Long id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Book addBook(Book book) {
        return booksRepository.save(book);
    }

    public Book updateBook(long id, String title, String ISBN) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setTitle(title);
            book.get().setISBN(ISBN);
            return booksRepository.save(book.get());
        }
        return null;
    }

    public void deleteBook(long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            booksRepository.delete(book.get());
        } else {
            throw new BookNotFoundException();
        }
    }
}
