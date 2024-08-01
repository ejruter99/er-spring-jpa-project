package com.galvanize.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class GenresController {

    GenreService genreService;

     public GenresController(GenreService genreService){
         this.genreService = genreService;
     }

     @GetMapping("/api/genres")
    public ResponseEntity<GenresList> getGenres() {
         GenresList genresList = genreService.getGenres();
         return genresList.isEmpty() ? ResponseEntity.noContent().build() :
                 ResponseEntity.ok(genresList);
     }

     @GetMapping("/api/genres/{id}")
    public Genre getGenreById(@PathVariable Long id) {
         return genreService.getGenre(id);
     }

     @PostMapping("/api/genres")
     public Genre addGrenre(@RequestBody Genre genre) {
         return genreService.addGenre(genre);
     }

     @PatchMapping("/api/genres/{id}")
     public Genre updateGenre(@PathVariable Long id,
                              @RequestBody UpdateGenreRequest update) {
         Genre genre = genreService.updateGenre(id, update.getBooks());
         genre.setBooks(update.getBooks());
         return genre;
     }

     @DeleteMapping("/api/genres/{id}")
     public ResponseEntity deleteGenre(@PathVariable Long id) {
         try {
             genreService.deleteGenre(id);
         } catch (GenreNotFoundException e) {
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.accepted().build();
     }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void genreNotFoundExceptionHandler(GenreNotFoundException e) {

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidGenreExceptionHandler(InvalidGenreException e) {

    }

}
