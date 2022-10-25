package ru.vitaliy.bookShop.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vitaliy.bookShop.entity.*;
import ru.vitaliy.bookShop.service.BookOrderProductService;
import ru.vitaliy.bookShop.service.BookOrderService;
import ru.vitaliy.bookShop.service.BookService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/public/book")
public class BookWebController {
    private final BookOrderService bookOrderService;
    private final BookService bookService;
    private final BookOrderProductService bookOrderProductService;

    @Autowired
    public BookWebController(BookOrderService bookOrderService, BookService bookService,
                             BookOrderProductService bookOrderProductService) {
        this.bookOrderService = bookOrderService;
        this.bookService = bookService;
        this.bookOrderProductService = bookOrderProductService;
    }

    @PostMapping("/addBookOrder")
    public String addBookOrder(@RequestParam(value = "bookId") int bookId, Model model) {
        Book book = bookService.getBookById(bookId);
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        if (bookOrderService.getBookOrderByUser(user.getUsername()) == null) {
            bookOrderService.createOrder(user);
        }

        BookOrder bookOrder = bookOrderService.getBookOrderByUser(user.getUsername());

        BookOrderProduct bookOrderProduct =
                bookOrderProductService.getBookOrderProductByBookIdAndBookOrderId(bookId, bookOrder.getId());

        if (bookOrderProduct == null) {
            bookOrderProduct = new BookOrderProduct();
            bookOrderProduct.setBookOrder(bookOrder);
            bookOrderProduct.setBookOrderId(bookOrder.getId());
            bookOrderProduct.setBook(book);
            bookOrderProduct.setBookId(bookId);
            bookOrderProduct.setCost(book.getCost());
            bookOrderProduct.setCount(1);
        } else {
            bookOrderProduct.setCount(bookOrderProduct.getCount() + 1);
        }

        bookOrderProductService.createProduct(bookOrderProduct);

        model.addAttribute("book", book);
        return "/public/book";
    }
}
