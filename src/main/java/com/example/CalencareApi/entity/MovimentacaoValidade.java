package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
public class MovimentacaoValidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tipoMovimentacao;
    private Integer quantidade;
    private LocalDateTime dtCriacao;
    private Integer bitStatus;
    @ManyToOne private Validade validade;
}
