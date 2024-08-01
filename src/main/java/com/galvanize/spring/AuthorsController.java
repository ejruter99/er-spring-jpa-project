package com.galvanize.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class AuthorsController {

    AuthorsService authorsService;

    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping("/api/authors/{id}")
    public Author getAuthorById(@PathVariable long id) {
        return authorsService.getAuthor(id);
    }

    @GetMapping("/api/authors")
    public ResponseEntity<AuthorsList> getAuthors(@RequestParam(required = false) Integer birthYear) {
        AuthorsList authorsList;
        if (birthYear != null) {
            authorsList = authorsService.getAuthors(birthYear);
        } else {
            authorsList = authorsService.getAuthors();
        }
        return authorsList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(authorsList);
    }

    @PostMapping("/api/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorsService.addAuthor(author);
    }

    @PatchMapping("/api/authors/{id}")
    public Author updateAuthor(@PathVariable Long id,
                               @RequestBody UpdateAuthorRequest update) {
        Author author = authorsService.updateAuthor(id, update.getBirthYear(), update.getBooks());
        author.setBirthYear(update.getBirthYear());
        author.setBooks(update.getBooks());
        return author;
    }

    @DeleteMapping("/api/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable Long id) {
        try {
            authorsService.deleteAuthor(id);
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void authorNotFoundException(AuthorNotFoundException e) {

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAuthorException(InvalidAuthorException e) {

    }

}
