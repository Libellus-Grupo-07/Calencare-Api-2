package com.example.CalencareApi.dto.despesa;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DespesaAtualizarDto {
    private String nome;
    private String observacao;
    private Double valor;
    private String formaPagamento;
    private LocalDateTime dtPagamento;
    private Integer bitStatus;
}