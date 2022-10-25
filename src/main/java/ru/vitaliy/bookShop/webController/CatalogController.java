package ru.vitaliy.bookShop.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.service.BookService;

    @Controller
    @RequestMapping("public/catalog")
    public class CatalogController {
        private final BookService bookService;

    @Autowired
    public CatalogController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getBooks")
    public String getBooks(@RequestParam(value = "bookName") String bookName, Model model) {
        model.addAttribute("books", bookService.getBooksByName(bookName));
        return "/public/catalog";
    }
}
