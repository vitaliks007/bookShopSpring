package ru.vitaliy.bookShop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bookOrders")
@Getter
@Setter
@ToString
public class BookOrder {
    @Id
    @Column(name = "book_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int status;

    private Date date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookOrder")
    private Set<BookOrderProduct> bookOrderProducts;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private int userId;
}
