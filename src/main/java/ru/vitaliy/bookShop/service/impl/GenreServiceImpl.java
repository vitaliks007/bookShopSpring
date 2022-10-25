package ru.vitaliy.bookShop.service.impl;

import org.springframework.stereotype.Service;
import ru.vitaliy.bookShop.dao.GenreDao;
import ru.vitaliy.bookShop.entity.Genre;
import ru.vitaliy.bookShop.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void createGenre(Genre genre) {
        genreDao.save(genre);
    }

    @Override
    public List<Genre> getGenres() {
        return genreDao.findAll();
    }

    @Override
    public Genre getGenreById(int genreId) {
        return genreDao.findById(genreId).get();
    }
}
