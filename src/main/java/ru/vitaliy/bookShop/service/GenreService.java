package ru.vitaliy.bookShop.service;

import ru.vitaliy.bookShop.entity.Genre;

import java.util.List;

public interface GenreService {
    void createGenre(Genre genre);

    List<Genre> getGenres();

    Genre getGenreById(int genreId);
}
