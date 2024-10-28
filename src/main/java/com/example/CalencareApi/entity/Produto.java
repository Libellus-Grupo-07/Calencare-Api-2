package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String marca;
    private LocalDateTime dtCriacao;
    private Integer bitStatus;
    @ManyToOne
    private CategoriaProduto categoriaProduto;
    @ManyToOne
    private Empresa empresa;
}
