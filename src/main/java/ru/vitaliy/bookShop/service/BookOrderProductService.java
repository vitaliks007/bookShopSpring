package ru.vitaliy.bookShop.service;

import ru.vitaliy.bookShop.entity.BookOrderProduct;

import java.util.List;

public interface BookOrderProductService {
    void createProduct(BookOrderProduct bookOrderProduct);

    List<BookOrderProduct> getBookOrderProductsByBookOrderId(int id);

    BookOrderProduct getBookOrderProductByBookIdAndBookOrderId(int bookId, int bookOrderId);

    void deleteBookOrderProduct(int id);
}
