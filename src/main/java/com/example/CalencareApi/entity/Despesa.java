package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String observacao;
    private Double valor;
    private String formaPagamento;
    private LocalDateTime dtPagamento;
    private LocalDateTime dtCriacao;
    private Integer bitStatus;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private CategoriaDespesa categoriaDespesa;
}