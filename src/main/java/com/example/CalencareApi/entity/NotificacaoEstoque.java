package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
public class NotificacaoEstoque {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeProduto;
    private String nivelEstoque;
    private Integer quantidade;
    private Integer lido;
    private Integer excluido;
    private LocalDateTime dtCriacao;
    private LocalDateTime dtLeitura;
    private LocalDateTime dtExclusao;
    @ManyToOne
    private Produto produto;
}
