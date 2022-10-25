package ru.vitaliy.bookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitaliy.bookShop.entity.Genre;
import ru.vitaliy.bookShop.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createGenre(@RequestBody Genre genre) {
        genreService.createGenre(genre);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Genre>> getGenres() {
        final List<Genre> genres = genreService.getGenres();

        return genres != null && !genres.isEmpty()
                ? new ResponseEntity<>(genres, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable(name = "id") int id) {
        final Genre genre = genreService.getGenreById(id);

        return genre != null
                ? new ResponseEntity<>(genre, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
