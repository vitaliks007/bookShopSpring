package ru.vitaliy.bookShop.service;

import ru.vitaliy.bookShop.entity.BookOrder;
import ru.vitaliy.bookShop.entity.User;

import java.util.Date;
import java.util.List;

public interface BookOrderService {
    void updateOrder(BookOrder bookOrder);

    void createOrder(User user);

    boolean cancelOrder(int id);

    boolean changeOrderStatus(int id, int status);

    BookOrder getBookOrderByUser(String username);

    boolean sellBook(int orderId);

    boolean deleteBookOrder(int orderId);

    List<BookOrder> getBookOrders();

    BookOrder getBookOrderById(int bookOrderId);

    List<BookOrder> getBookOrdersByDate();

//    List<BookOrder> getBookOrdersByCost();

    List<BookOrder> getBookOrdersByStatus();

    List<BookOrder> getBookOrdersByRangeDate(Date start, Date end);

//    List<BookOrder> getBookOrdersByRangeCost(Date start, Date end);
//
//    int getSumCostByRange(Date start, Date end);

    int getCountCompletedBookOrdersByRange(Date start, Date end);

//    Map.Entry<Book, User> getBookOrderInfoById(int id);

}
