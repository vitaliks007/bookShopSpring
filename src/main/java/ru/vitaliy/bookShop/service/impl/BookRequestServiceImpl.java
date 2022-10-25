package ru.vitaliy.bookShop.service.impl;

import org.springframework.stereotype.Service;
import ru.vitaliy.bookShop.dao.BookDao;
import ru.vitaliy.bookShop.dao.BookRequestDao;
import ru.vitaliy.bookShop.entity.BookRequest;
import ru.vitaliy.bookShop.service.BookRequestService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRequestServiceImpl implements BookRequestService {
    private final BookRequestDao bookRequestDao;
    private final BookDao bookDao;

    public BookRequestServiceImpl(BookRequestDao bookRequestDao, BookDao bookDao) {
        this.bookRequestDao = bookRequestDao;
        this.bookDao = bookDao;
    }

    @Override
    public void createRequest(BookRequest bookRequest) {
        bookRequestDao.save(bookRequest);
    }

    @Override
    public List<BookRequest> getBookRequests() {
        return new ArrayList<>(bookRequestDao.findAll());
    }

    @Override
    public List<BookRequest> getBookRequestsByCount() {
        return bookRequestDao.findAll().stream()
                .collect(Collectors.groupingBy(t -> bookDao.getById(t.getBookId())))
                .values().stream()
                .sorted(Comparator.comparing(List::size, Comparator.reverseOrder()))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public List<BookRequest> getBookRequestsByAlph() {
        return bookRequestDao.findAll().stream()
                .sorted(Comparator.comparing(bookRequest -> bookDao.getById(bookRequest.getBookId()).getName()))
                .collect(Collectors.toList());
    }

}
