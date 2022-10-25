package ru.vitaliy.bookShop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String roles;

    private String name;

    private String surname;

    private int birthday;

    private String email;

    private String phone;
}
