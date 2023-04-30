package ru.vitaliy.bookShop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genres")
@Getter
@Setter
@ToString
public class Genre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String genreName;

    private String genreNameRus;

//    @JsonBackReference
    @ManyToMany
    private Set<Book> books;
}
