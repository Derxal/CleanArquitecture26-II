package com.example.demo.infrestructure.adapter.repositories;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;


    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;


}
