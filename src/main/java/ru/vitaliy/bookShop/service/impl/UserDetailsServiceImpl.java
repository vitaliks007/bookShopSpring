package ru.vitaliy.bookShop.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vitaliy.bookShop.dao.UserDao;
import ru.vitaliy.bookShop.entity.User;
import ru.vitaliy.bookShop.entity.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
        return user.map(UserDetailsImpl::new).get();
    }
}
