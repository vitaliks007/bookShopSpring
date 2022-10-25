package ru.vitaliy.bookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.entity.DTO.IntegerDTO;
import ru.vitaliy.bookShop.entity.Genre;
import ru.vitaliy.bookShop.service.BookService;
import ru.vitaliy.bookShop.service.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @PatchMapping(path = "/add/{id}")
    public ResponseEntity<?> addBook(@PathVariable(name = "id") int id, @RequestBody IntegerDTO count) {
        final boolean updated = bookService.addBook(id, count.getValue());

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PatchMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") int id) {
        final boolean deleted = bookService.deleteBook(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping()
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        List<Genre> bdGenres = genreService.getGenres();
        List<Genre> genres = book.getGenres().stream()
                .filter(bookGenre -> bdGenres.stream()
                .noneMatch(bdGenre -> Objects.equals(bdGenre.getGenreName(), bookGenre.getGenreName())))
                .collect(Collectors.toList());
        genres.forEach(genreService::createGenre);
        bookService.createBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getBooks() {
        final List<Book> books = bookService.getBooks();

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "id") int id) {
        final Book book = bookService.getBookById(id);

        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sorted/alph")
    public ResponseEntity<List<Book>> getBooksByAlph() {
        final List<Book> books = bookService.getBooksByAlph();

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sorted/date")
    public ResponseEntity<List<Book>> getBooksByDate() {
        final List<Book> books = bookService.getBooksByDate();

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sorted/cost")
    public ResponseEntity<List<Book>> getBooksByCost() {
        final List<Book> books = bookService.getBooksByCost();

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sorted/count")
    public ResponseEntity<List<Book>> getBooksByCount() {
        final List<Book> books = bookService.getBooksByCount();

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping(value = "/sorted/sixMonthOlder/date")
//    public ResponseEntity<List<Book>> getSixMonthOlderBooksByDate() {
//        final List<Book> books = bookService.getSixMonthOlderBooksByDate();
//
//        return books != null &&  !books.isEmpty()
//                ? new ResponseEntity<>(books, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(value = "/sorted/sixMonthOlder/cost")
//    public ResponseEntity<List<Book>> getSixMonthOlderBooksByCost() {
//        final List<Book> books = bookService.getSixMonthOlderBooksByCost();
//
//        return books != null &&  !books.isEmpty()
//                ? new ResponseEntity<>(books, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping(value = "/description/{id}")
    public ResponseEntity<String> getBookDescriptionById(@PathVariable(name = "id") int id) {
        final String bookDescription = bookService.getBookDescriptionById(id);

        return bookDescription != null
                ? new ResponseEntity<>(bookDescription, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
