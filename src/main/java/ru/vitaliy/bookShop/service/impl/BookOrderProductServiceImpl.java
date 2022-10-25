package ru.vitaliy.bookShop.service.impl;

import org.springframework.stereotype.Service;
import ru.vitaliy.bookShop.dao.BookOrderProductDao;
import ru.vitaliy.bookShop.entity.BookOrderProduct;
import ru.vitaliy.bookShop.service.BookOrderProductService;

import java.util.List;
import java.util.Optional;

@Service
public class BookOrderProductServiceImpl implements BookOrderProductService {
    private final BookOrderProductDao bookOrderProductDao;

    public BookOrderProductServiceImpl(BookOrderProductDao bookOrderProductDao) {
        this.bookOrderProductDao = bookOrderProductDao;
    }

    @Override
    public void createProduct(BookOrderProduct bookOrderProduct) {
        bookOrderProductDao.save(bookOrderProduct);
    }

    @Override
    public List<BookOrderProduct> getBookOrderProductsByBookOrderId(int id) {
        return bookOrderProductDao.findByBookOrderId(id).get();
    }

    @Override
    public BookOrderProduct getBookOrderProductByBookIdAndBookOrderId(int bookId, int bookOrderId) {
        Optional<BookOrderProduct> bookOrderProduct =
                bookOrderProductDao.findByBookIdAndBookOrderId(bookId, bookOrderId);
        if (bookOrderProduct.isEmpty()) {
            return null;
        } else {
            return bookOrderProduct.get();
        }
    }

    @Override
    public void deleteBookOrderProduct(int id) {
        bookOrderProductDao.deleteById(id);
    }
}
