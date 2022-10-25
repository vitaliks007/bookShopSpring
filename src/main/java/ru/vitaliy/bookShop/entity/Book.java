package ru.vitaliy.bookShop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(targetEntity = Author.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private Author author;

    @Column(name = "author_id")
    private int authorId;

    private boolean status;

    private int count;

    @ManyToMany(mappedBy = "books")
    private Set<Genre> genres;

    private int cost;
    
    private String description;

    private String icon;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date publishDate;
}
