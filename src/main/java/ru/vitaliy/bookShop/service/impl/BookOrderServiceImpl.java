package ru.vitaliy.bookShop.service.impl;

import org.springframework.stereotype.Service;
import ru.vitaliy.bookShop.dao.*;
import ru.vitaliy.bookShop.entity.*;
import ru.vitaliy.bookShop.service.BookOrderService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookOrderServiceImpl implements BookOrderService {
    private final BookOrderDao bookOrderDao;
    private final BookRequestDao bookRequestDao;
    private final BookDao bookDao;
    private final UserDao UserDao;
    private final BookOrderProductDao bookOrderProductDao;

    public BookOrderServiceImpl(BookOrderDao bookOrderDao, BookRequestDao bookRequestDao, BookDao bookDao,
                                UserDao UserDao, BookOrderProductDao bookOrderProductDao) {
        this.bookOrderDao = bookOrderDao;
        this.bookRequestDao = bookRequestDao;
        this.bookDao = bookDao;
        this.UserDao = UserDao;
        this.bookOrderProductDao = bookOrderProductDao;
    }

    @Override
    public void updateOrder(BookOrder bookOrder) {
        bookOrderDao.save(bookOrder);
    }

    @Override
    public void createOrder(User user) {
        BookOrder bookOrder = new BookOrder();

        Set<BookOrderProduct> bookOrderProducts = new HashSet<>();
        bookOrder.setBookOrderProducts(bookOrderProducts);
        bookOrder.setStatus(0);
        bookOrder.setUser(user);
        bookOrder.setUserId(user.getId());
        bookOrderDao.save(bookOrder);
    }

    @Override
    public boolean cancelOrder(int id) {
        changeOrderStatus(id, -1);
        return true;
    }

    @Override
    public boolean changeOrderStatus(int id, int status) {
        BookOrder bookOrder = bookOrderDao.findById(id).get();
        bookOrder.setStatus(status);
        if (status == 1) {
            setDate(bookOrder);
        }
        bookOrderDao.save(bookOrder);
        return true;
    }

    @Override
    public BookOrder getBookOrderByUser(String username) {
        Optional<BookOrder> bookOrder = bookOrderDao.findFirstByUserUsernameAndStatus(username, 0);
        if (bookOrder.isEmpty()) {
            return null;
        } else {
            return bookOrder.get();
        }
    }

    @Override
    public boolean sellBook(int orderId) {
        BookOrder bookOrder = bookOrderDao.findById(orderId).get();

        bookOrder.setStatus(1);
        for (BookOrderProduct bookOrderProduct : bookOrder.getBookOrderProducts()) {
            Book book = bookDao.getById(bookOrderProduct.getBookId());

            if (book.getCount() < bookOrderProduct.getCount()) {
                BookRequest bookRequest = new BookRequest();
                bookRequest.setBookId(book.getId());
                bookRequestDao.save(bookRequest);
            }
        }

        Set<BookOrderProduct> bookOrderProducts = bookOrder.getBookOrderProducts();
        boolean isEnough = bookOrderProducts.stream().noneMatch(x -> x.getCount() > x.getBook().getCount());

        if (isEnough) {
            for (BookOrderProduct bookOrderProduct : bookOrderProducts) {
                bookOrderProduct.getBook().setCount(
                        bookOrderProduct.getBook().getCount() - bookOrderProduct.getCount()
                );
                bookOrderProductDao.save(bookOrderProduct);
            }
            setDate(bookOrder);
            bookOrderDao.save(bookOrder);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteBookOrder(int orderId) {
        BookOrder bookOrder = bookOrderDao.findById(orderId).get();
        bookOrderDao.delete(bookOrder);
        return true;
    }

    @Override
    public List<BookOrder> getBookOrders() {
        return new ArrayList<>(bookOrderDao.findAll());
    }

    @Override
    public BookOrder getBookOrderById(int bookOrderId) {
        return bookOrderDao.findById(bookOrderId).get();
    }

    @Override
    public List<BookOrder> getBookOrdersByDate() {
        return bookOrderDao.findAll().stream()
                .sorted(Comparator.comparing(BookOrder::getDate))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<BookOrder> getBookOrdersByCost() {
//        return bookOrderDao.findAll().stream()
//                .sorted(Comparator.comparing(bookOrder -> bookDao.getById(bookOrder.getBookId()).getCost()))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<BookOrder> getBookOrdersByStatus() {
        return bookOrderDao.findAll().stream()
                .sorted(Comparator.comparing(BookOrder::getStatus))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookOrder> getBookOrdersByRangeDate(Date start, Date end) {
        return getBookOrdersByRange(start, end)
                .sorted(Comparator.comparing(BookOrder::getDate))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<BookOrder> getBookOrdersByRangeCost(Date start, Date end) {
//        return getBookOrdersByRange(start, end)
//                .sorted(Comparator.comparing(bookOrder -> bookDao.getById(bookOrder.getBookId()).getCost()))
//                .collect(Collectors.toList());
//    }

//    @Override
//    public int getSumCostByRange(Date start, Date end) {
//        return getBookOrdersByRange(start, end)
//                .reduce(0, (x, y) -> x + bookDao.getById(y.getBookId()).getCost(), Integer::sum);
//    }

    @Override
    public int getCountCompletedBookOrdersByRange(Date start, Date end) {
        return (int) getBookOrdersByRange(start, end)
                .count();
    }

//    @Override
//    public Map.Entry<Book, User> getBookOrderInfoById(int id) {
//        BookOrder bookOrder = bookOrderDao.findById(id).get();
//        return new AbstractMap.SimpleEntry<Book, User>(bookDao.getById(bookOrder.getBookId()),
//                UserDao.getById(bookOrder.getUserId()));
//    }

    private Stream<BookOrder> getBookOrdersByRange(Date start, Date end) {
        return bookOrderDao.findAll().stream()
                .filter(t -> ((t.getStatus() == 1) && (t.getDate().after(start)) && (t.getDate().before(end))));
    }

    private void setDate(BookOrder bookOrder) {
        bookOrder.setDate(Date.from(Instant.now()));
    }
}
