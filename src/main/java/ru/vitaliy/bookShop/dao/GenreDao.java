package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitaliy.bookShop.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Integer> {
}
