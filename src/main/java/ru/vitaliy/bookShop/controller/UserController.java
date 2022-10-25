package ru.vitaliy.bookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitaliy.bookShop.entity.User;
import ru.vitaliy.bookShop.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService UserService;

    @Autowired
    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User user) {
        UserService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") int id) {
        final User user = UserService.getUserById(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        final List<User> users = UserService.getUsers();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
