package ru.vitaliy.bookShop.webController;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vitaliy.bookShop.entity.BookOrder;
import ru.vitaliy.bookShop.entity.BookOrderProduct;
import ru.vitaliy.bookShop.entity.User;
import ru.vitaliy.bookShop.entity.UserDetailsImpl;
import ru.vitaliy.bookShop.service.BookOrderProductService;
import ru.vitaliy.bookShop.service.BookOrderService;

import java.util.List;

@Controller
@RequestMapping("/private/cart")
public class CartController {
    private final BookOrderService bookOrderService;
    private final BookOrderProductService bookOrderProductService;

    public CartController(BookOrderService bookOrderService, BookOrderProductService bookOrderProductService) {
        this.bookOrderService = bookOrderService;
        this.bookOrderProductService = bookOrderProductService;
    }

    @PostMapping("/sellBook")
    public String sellBook(@RequestParam(value = "bookOrderId") int id, Model model) {
        bookOrderService.sellBook(id);

        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        BookOrder bookOrder = bookOrderService.getBookOrderById(id);
        List<BookOrderProduct> bookOrderProducts = bookOrderProductService.getBookOrderProductsByBookOrderId(id);

        String bookOrderStatus = null;
        switch (bookOrder.getStatus()) {
            case -1:
                bookOrderStatus = "Отменено";
                break;
            case 0:
                bookOrderStatus = "В корзине";
                break;
            case 1:
                bookOrderStatus = "В процессе";
                break;
            case 2:
                bookOrderStatus = "Доставлено";
                break;
            default:
                break;
        }

        model.addAttribute("user", user);
        model.addAttribute("bookOrder", bookOrder);
        model.addAttribute("bookOrderStatus", bookOrderStatus);
        model.addAttribute("bookOrderProducts", bookOrderProducts);
        return "/private/order";
    }

    @PostMapping("/cancelOrder")
    public String cancelOrder(@RequestParam(value = "bookOrderId") int id) {
        bookOrderService.cancelOrder(id);
        return "redirect:/private/cart";
    }

    @PostMapping("/deleteOrderProduct")
    public String deleteOrder(@RequestParam(value = "bookOrderProductId") int id) {
        bookOrderProductService.deleteBookOrderProduct(id);
        return "redirect:/private/cart";
    }
}
