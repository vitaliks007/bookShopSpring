package ru.vitaliy.bookShop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "bookOrderProducts")
@Getter
@Setter
@ToString
public class BookOrderProduct {
    @Id
    @Column(name = "book_order_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @Column(name = "book_id")
    private int bookId;

    private int count;

    private int cost;

    @ManyToOne(targetEntity = BookOrder.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_order_id", insertable = false, updatable = false)
    private BookOrder bookOrder;

    @Column(name = "book_order_id")
    private int bookOrderId;
}
