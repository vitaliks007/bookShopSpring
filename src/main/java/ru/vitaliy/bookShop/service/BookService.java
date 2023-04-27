package ru.vitaliy.bookShop.service;

import ru.vitaliy.bookShop.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooksByName(String name);

    boolean addBook(int id, int count);

    boolean deleteBook(int id);

    void createBook(Book book);

    List<Book> getBooks();

    Book getBookById(int id);

    List<Book> getBooksByAlph();

    List<Book> getBooksByDate();

    List<Book> getBooksByCost();

    List<Book> getBooksOrderByCostAsc();
    List<Book> getBooksOrderByCostDesc();

    List<Book> getBooksByCount();

//    List<Book> getSixMonthOlderBooksByDate();
//
//    List<Book> getSixMonthOlderBooksByCost();

    String getBookDescriptionById(int id);

    List<Book> getNewBooks();

    List<Book> getDiscountBook();
}
