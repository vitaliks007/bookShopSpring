package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitaliy.bookShop.entity.BookOrderProduct;

import java.util.List;
import java.util.Optional;

public interface BookOrderProductDao extends JpaRepository<BookOrderProduct, Integer> {
    Optional<List<BookOrderProduct>> findByBookOrderId(int id);
    Optional<BookOrderProduct> findByBookIdAndBookOrderId(int bookId, int bookOrderId);
}
