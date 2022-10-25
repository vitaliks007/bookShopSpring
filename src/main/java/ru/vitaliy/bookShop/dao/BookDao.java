package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliy.bookShop.entity.Author;
import ru.vitaliy.bookShop.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Integer> {
    Optional<List<Book>> findByNameContaining(String name);
}
