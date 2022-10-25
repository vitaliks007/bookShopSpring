package ru.vitaliy.bookShop.service;

import ru.vitaliy.bookShop.entity.BookRequest;

import java.util.List;

public interface BookRequestService {
    void createRequest(BookRequest bookRequest);

    List<BookRequest> getBookRequests();

    List<BookRequest> getBookRequestsByCount();

    List<BookRequest> getBookRequestsByAlph();

}
