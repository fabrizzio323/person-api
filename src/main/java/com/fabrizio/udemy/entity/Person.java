package com.fabrizio.udemy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name="first_name")
    private String firstName;
    @Column(nullable = false, name="last_name")
    private String lastName;
    @Column(nullable = false, name="address")
    private String address;
    @Column(nullable = false, name="gender")
    private String gender;
    @Column(nullable = false, name="email")
    private String email;
}
