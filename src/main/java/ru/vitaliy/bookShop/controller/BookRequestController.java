package ru.vitaliy.bookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.entity.BookRequest;
import ru.vitaliy.bookShop.service.BookRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class BookRequestController {
    private final BookRequestService bookRequestService;

    @Autowired
    public BookRequestController(BookRequestService bookRequestService) {
        this.bookRequestService = bookRequestService;
    }

    @PostMapping()
    public ResponseEntity<?> createRequest(@RequestBody BookRequest bookRequest) {
        bookRequestService.createRequest(bookRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<BookRequest>> getBookRequests() {
        final List<BookRequest> bookRequests = bookRequestService.getBookRequests();

        return bookRequests != null &&  !bookRequests.isEmpty()
                ? new ResponseEntity<>(bookRequests, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "sorted/count")
    public ResponseEntity<List<BookRequest>> getBookRequestsByCount() {
        final List<BookRequest> bookRequests = bookRequestService.getBookRequestsByCount();

        return bookRequests != null &&  !bookRequests.isEmpty()
                ? new ResponseEntity<>(bookRequests, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "sorted/alph")
    public ResponseEntity<List<BookRequest>> getBookRequestsByAlph() {
        final List<BookRequest> bookRequests = bookRequestService.getBookRequestsByAlph();

        return bookRequests != null &&  !bookRequests.isEmpty()
                ? new ResponseEntity<>(bookRequests, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
