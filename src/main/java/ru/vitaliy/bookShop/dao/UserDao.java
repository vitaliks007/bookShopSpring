package ru.vitaliy.bookShop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitaliy.bookShop.entity.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
