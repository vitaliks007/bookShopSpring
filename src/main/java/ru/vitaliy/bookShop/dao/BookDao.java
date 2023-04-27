package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitaliy.bookShop.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Integer> {
    Optional<List<Book>> findByNameContaining(String name);

    Optional<List<Book>> findTop12ByOrderByPublishDateDesc();

    Optional<List<Book>> findByDiscountGreaterThanOrderByDiscountDesc(int discount);

    Optional<List<Book>> findByOrderByCostAsc();
    Optional<List<Book>> findByOrderByCostDesc();
}
