package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitaliy.bookShop.entity.BookOrder;

import java.util.List;
import java.util.Optional;

public interface BookOrderDao extends JpaRepository<BookOrder, Integer> {
    Optional<List<BookOrder>> findByUserUsernameContainingAndStatus(String username, int status);
    Optional<BookOrder> findFirstByUserUsernameAndStatus(String username, int status);
}
