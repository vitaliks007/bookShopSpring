package ru.vitaliy.bookShop.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.service.BookService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/getNewBooks")
    public String getNewBooks(Model model) {
        model.addAttribute("books", bookService.getNewBooks());
        return "/public/catalog";
    }

    @GetMapping("/getDiscountBooks")
    public String getDiscountBooks(Model model) {
        model.addAttribute("books", bookService.getDiscountBook());
        return "/public/catalog";
    }

    @GetMapping("/getSortedBooks")
    public String getSortedBooks(@RequestParam(value = "sortType") String sortType, Model model) {
        List<Book> books;
        switch (sortType) {
            case "costAsc":
                books = bookService.getBooksOrderByCostAsc();
                break;
            case "costDesc":
                books = bookService.getBooksOrderByCostDesc();
                break;
            default:
                books = bookService.getBooks();
                break;
        }
        model.addAttribute("books", books);
        return "/public/catalog";
    }
}
