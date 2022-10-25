package ru.vitaliy.bookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitaliy.bookShop.entity.Author;
import ru.vitaliy.bookShop.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping()
    public ResponseEntity<?> addAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable(name = "id") int id) {
        final Author author = authorService.getAuthorById(id);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Author>> getAuthors() {
        final List<Author> authors = authorService.getAuthors();

        return authors != null &&  !authors.isEmpty()
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
