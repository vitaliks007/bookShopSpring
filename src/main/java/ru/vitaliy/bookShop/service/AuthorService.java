package ru.vitaliy.bookShop.service;

import ru.vitaliy.bookShop.entity.Author;

import java.util.List;

public interface AuthorService {
    void addAuthor(Author author);
    Author getAuthorById(int id);
    List<Author> getAuthors();
}
