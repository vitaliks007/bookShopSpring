package ru.vitaliy.bookShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitaliy.bookShop.entity.Book;
import ru.vitaliy.bookShop.entity.BookOrder;
import ru.vitaliy.bookShop.entity.User;
import ru.vitaliy.bookShop.entity.DTO.DateRangeDTO;
import ru.vitaliy.bookShop.entity.DTO.IntegerDTO;
import ru.vitaliy.bookShop.service.BookOrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class BookOrderController {
    private final BookOrderService bookOrderService;

    @Autowired
    public BookOrderController(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody BookOrder bookOrder) {
        bookOrderService.updateOrder(bookOrder);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable(name = "id") int id) {
        final boolean canceled = bookOrderService.cancelOrder(id);

        return canceled
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PatchMapping(path = "/changeStatus/{id}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable(name = "id") int id, @RequestBody IntegerDTO status) {
        final boolean updated = bookOrderService.changeOrderStatus(id, status.getValue());

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

//    @PatchMapping(path = "/sell/{id}")
//    public ResponseEntity<?> sellBook(@PathVariable(name = "id") int id) {
//        final boolean updated = bookOrderService.sellBook(id);
//
//        return updated
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }

    @GetMapping()
    public ResponseEntity<List<BookOrder>> getBookOrders() {
        final List<BookOrder> bookOrders = bookOrderService.getBookOrders();

        return bookOrders != null &&  !bookOrders.isEmpty()
                ? new ResponseEntity<>(bookOrders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/sorted/date")
    public ResponseEntity<List<BookOrder>> getBookOrdersByDate() {
        final List<BookOrder> bookOrders = bookOrderService.getBookOrdersByDate();

        return bookOrders != null &&  !bookOrders.isEmpty()
                ? new ResponseEntity<>(bookOrders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping(value = "/sorted/cost")
//    public ResponseEntity<List<BookOrder>> getBookOrdersByCost() {
//        final List<BookOrder> bookOrders = bookOrderService.getBookOrdersByCost();
//
//        return bookOrders != null &&  !bookOrders.isEmpty()
//                ? new ResponseEntity<>(bookOrders, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping(value = "/sorted/status")
//    public ResponseEntity<List<BookOrder>> getBookOrdersByStatus() {
//        final List<BookOrder> bookOrders = bookOrderService.getBookOrdersByStatus();
//
//        return bookOrders != null &&  !bookOrders.isEmpty()
//                ? new ResponseEntity<>(bookOrders, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping(value = "/range/sorted/date")
//    public ResponseEntity<List<BookOrder>> getBookOrdersByRangeDate(@RequestBody DateRangeDTO range) {
//        final List<BookOrder> bookOrders =
//                bookOrderService.getBookOrdersByRangeDate(range.getStart(), range.getEnd());
//
//        return bookOrders != null &&  !bookOrders.isEmpty()
//                ? new ResponseEntity<>(bookOrders, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping(value = "/range/sorted/cost")
//    public ResponseEntity<List<BookOrder>> getBookOrdersByRangeCost(@RequestBody DateRangeDTO range) {
//        final List<BookOrder> bookOrders =
//                bookOrderService.getBookOrdersByRangeCost(range.getStart(), range.getEnd());
//
//        return bookOrders != null &&  !bookOrders.isEmpty()
//                ? new ResponseEntity<>(bookOrders, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping(value = "/range/sumCost")
//    public ResponseEntity<IntegerDTO> getSumCostByRange(@RequestBody DateRangeDTO range) {
//        final IntegerDTO sumCost = new IntegerDTO();
//        sumCost.setValue(bookOrderService.getSumCostByRange(range.getStart(), range.getEnd()));
//
//        return new ResponseEntity<IntegerDTO>(sumCost, HttpStatus.OK);
//    }

    @GetMapping(value = "/range/countCompleted")
    public ResponseEntity<IntegerDTO> getCountCompletedBookOrdersByRange(@RequestBody DateRangeDTO range) {
        final IntegerDTO countCompleted = new IntegerDTO();
        countCompleted.setValue(bookOrderService.getCountCompletedBookOrdersByRange(range.getStart(), range.getEnd()));

        return new ResponseEntity<IntegerDTO>(countCompleted, HttpStatus.OK);
    }

//    @GetMapping(value = "/info/{id}")
//    public ResponseEntity<Map.Entry<Book, User>> getBookOrderInfoById(@PathVariable(name = "id") int id) {
//        final Map.Entry<Book, User> info = bookOrderService.getBookOrderInfoById(id);
//
//        return info != null
//                ? new ResponseEntity<>(info, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
