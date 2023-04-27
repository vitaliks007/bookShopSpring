package ru.vitaliy.bookShop.service.impl;

import org.springframework.stereotype.Service;
import ru.vitaliy.bookShop.dao.BookDao;
import ru.vitaliy.bookShop.dao.BookOrderDao;
import ru.vitaliy.bookShop.dao.BookRequestDao;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.entity.BookRequest;
import ru.vitaliy.bookShop.service.BookService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final BookOrderDao bookOrderDao;
    private final BookRequestDao bookRequestDao;

    public BookServiceImpl(BookDao bookDao, BookOrderDao bookOrderDao,
                           BookRequestDao bookRequestDao) {
        this.bookDao = bookDao;
        this.bookOrderDao = bookOrderDao;
        this.bookRequestDao = bookRequestDao;
    }

    @Override
    public List<Book> getBooksByName(String name) {
        return bookDao.findByNameContaining(name).get();
    }

    @Override
    public boolean addBook(int id, int count) {
        Book book = bookDao.findById(id).get();
        List<BookRequest> bookRequests = bookRequestDao.findAll();
        book.setCount(book.getCount() + count);
        book.setStatus(true);
        bookDao.save(book);

        bookRequests.forEach(bookRequest -> {
            if ((bookRequest.getBookId() == book.getId()) && (!bookRequest.isStatus())) {
                bookRequest.setStatus(true);
                bookRequest.setDate(Date.from(Instant.now()));
            }
        });
        return true;
    }

    @Override
    public boolean deleteBook(int id) {
        Book book = bookDao.findById(id).get();
        book.setCount(0);
        book.setStatus(false);
        bookDao.save(book);
        return true;
    }

    @Override
    public void createBook(Book book) {
        bookDao.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return new ArrayList<>(bookDao.findAll());
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.findById(id).get();
    }

    @Override
    public List<Book> getBooksByAlph() {
        return bookDao.findAll().stream()
                .sorted(Comparator.comparing(Book::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByDate() {
        return bookDao.findAll().stream()
                .sorted(Comparator.comparing(Book::getPublishDate)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByCost() {
        return bookDao.findAll().stream()
                .sorted(Comparator.comparing(Book::getCost))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksOrderByCostAsc() {
        return bookDao.findByOrderByCostAsc().get();
    }

    @Override
    public List<Book> getBooksOrderByCostDesc() {
        return bookDao.findByOrderByCostDesc().get();
    }

    @Override
    public List<Book> getBooksByCount() {
        return bookDao.findAll().stream()
                .sorted(Comparator.comparing(Book::getCount))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Book> getSixMonthOlderBooksByDate() {
//        return bookRequestDao.findAll().stream()
//                .filter(bookRequest -> getSixMonthOlderBooks().collect(Collectors.toList())
//                        .contains(getBookById(bookRequest.getBookId())))
//                .collect(Collectors.groupingBy(t -> getBookById(t.getBookId())))
//                .values().stream()
//                .map(bookRequests -> bookRequests.stream()
//                        .max(Comparator.comparing(this::getBookOrderRequestDate)).get())
//                .sorted(Comparator.comparing(this::getBookOrderRequestDate))
//                .map(t -> getBookById(t.getBookId()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Book> getSixMonthOlderBooksByCost() {
//        return getSixMonthOlderBooks()
//                .sorted(Comparator.comparing(Book::getCost))
//                .collect(Collectors.toList());
//    }

    @Override
    public String getBookDescriptionById(int id) {
        return bookDao.findById(id).get().getDescription();
    }

//    private Stream<Book> getSixMonthOlderBooks() {
//        return bookOrderDao.findAll().stream()
//                .collect(Collectors.groupingBy(t -> getBookById(t.getBookId())))
//                .values().stream()
//                .map(bookOrders -> bookOrders.stream()
//                        .max(Comparator.comparing(this::getBookOrderRequestDate)).get())
//                .filter(bookOrder -> isSixMonthOlder(getBookOrderRequestDate(bookOrder)))
//                .map(t -> getBookById(t.getBookId()));
//    }

//    private Date getBookOrderRequestDate(BookOrder bookOrder) {
//        return bookOrder.getDate() == null ? getBookById(bookOrder.getBookId()).getPublishDate() : bookOrder.getDate();
//    }

    private Date getBookOrderRequestDate(BookRequest bookRequest) {
        return bookRequest.getDate() == null ? getBookById(bookRequest.getBookId())
                .getPublishDate() : bookRequest.getDate();
    }

    private boolean isSixMonthOlder(Date bookOrderDate) {
        Calendar bookOrderCalendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        bookOrderCalendar.setTime(bookOrderDate);
        nowCalendar.setTime(Date.from(Instant.now()));
        bookOrderCalendar.add(Calendar.MONTH, 6);
        return nowCalendar.after(bookOrderCalendar);
    }

    @Override
    public List<Book> getNewBooks() {
        return bookDao.findTop12ByOrderByPublishDateDesc().get();
    }

    @Override
    public List<Book> getDiscountBook() {
        return bookDao.findByDiscountGreaterThanOrderByDiscountDesc(0).get();
    }
}
