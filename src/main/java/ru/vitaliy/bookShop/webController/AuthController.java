package ru.vitaliy.bookShop.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vitaliy.bookShop.entity.User;
import ru.vitaliy.bookShop.service.UserService;

import java.util.Objects;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(value = "username") String username,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "surname") String surname,
                          @RequestParam(value = "birthday") int birthday ,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "phone") String phone,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "passwordRepeat") String passwordRepeat,
                          Model model) {

        model.addAttribute("username", username);
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        model.addAttribute("birthday", birthday);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        if (userService.getUserByUsername(username) != null) {
            model.addAttribute("isCorrectUsername", false);
            return "/auth/registration";
        } else if (!Objects.equals(password, passwordRepeat)) {
            model.addAttribute("isCorrectPassword", false);
            return "/auth/registration";
        }  else {
            User user = new User();
            user.setUsername(username);
            user.setName(name);
            user.setSurname(surname);
            user.setBirthday(birthday);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setRoles("ROLE_USER");
            userService.addUser(user);
            return "/auth/login";
        }

    }
}
