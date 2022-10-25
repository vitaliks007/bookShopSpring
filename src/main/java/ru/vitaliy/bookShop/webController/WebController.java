package ru.vitaliy.bookShop.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vitaliy.bookShop.entity.BookOrderProduct;
import ru.vitaliy.bookShop.entity.User;
import ru.vitaliy.bookShop.entity.UserDetailsImpl;
import ru.vitaliy.bookShop.service.BookOrderProductService;
import ru.vitaliy.bookShop.service.BookOrderService;
import ru.vitaliy.bookShop.service.BookService;

import java.util.List;

@Controller
public class WebController {
    private final BookService bookService;
    private final BookOrderService bookOrderService;
    private final BookOrderProductService bookOrderProductService;

    @Autowired
    public WebController(BookService bookService, BookOrderService bookOrderService,
                         BookOrderProductService bookOrderProductService) {
        this.bookService = bookService;
        this.bookOrderService = bookOrderService;
        this.bookOrderProductService = bookOrderProductService;
    }

    @GetMapping("/public/catalog")
    public String catalog(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "public/catalog";
    }

    @GetMapping("/private/cart")
    public String cart(Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        if (bookOrderService.getBookOrderByUser(user.getUsername()) == null) {
            bookOrderService.createOrder(user);
        }

        int bookOrderId = bookOrderService
                .getBookOrderByUser(user.getUsername()).getId();

        List<BookOrderProduct> bookOrderProducts =
                bookOrderProductService.getBookOrderProductsByBookOrderId(bookOrderId);

        int sum = bookOrderProducts.stream().reduce(0, (x,y) -> x + y.getCost(), Integer::sum);
        String sumText = "Итого: " + sum;

        model.addAttribute("bookOrderProducts", bookOrderProducts);
        model.addAttribute("bookOrderProductsCount", bookOrderProducts.size());
        model.addAttribute("bookOrderProductsSum", sumText);
        return "private/cart";
    }

    @GetMapping("/public/book")
    public String bookPage(@RequestParam(value = "bookId") int id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "/public/book";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/registration")
    public String registration(Model model) {
        return "auth/registration";
    }
}
