package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliy.bookShop.entity.Author;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.entity.BookRequest;

import java.util.List;

public interface BookRequestDao extends JpaRepository<BookRequest, Integer> {
}
