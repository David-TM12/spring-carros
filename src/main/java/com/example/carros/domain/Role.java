package com.example.carros.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String login;
    private String senha;
    private String email;

}
