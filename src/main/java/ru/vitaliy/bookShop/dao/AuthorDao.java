package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitaliy.bookShop.entity.Author;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Integer> {
}
