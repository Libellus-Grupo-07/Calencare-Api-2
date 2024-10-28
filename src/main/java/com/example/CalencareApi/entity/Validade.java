package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
public class Validade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    LocalDateTime dtValidade;
    LocalDateTime dtCriacao;
    String descricao;
    Integer bitStatus;
    @ManyToOne
    Produto produto;
}
