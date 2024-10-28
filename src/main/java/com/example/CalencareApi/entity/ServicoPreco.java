package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServicoPreco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private Double preco;
    private Integer duracao;
    private Double comissao;
    private Integer bitStatus;
    private String categoria;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private Servico servico;

}