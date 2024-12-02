package com.example.CalencareApi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NotificacaoEstoqueConsultaDto {
    private Integer id;
    private String nomeProduto;
    private String nivelEstoque;
    private Integer quantidade;
    private Integer lido;
    private Integer excluido;
    private LocalDateTime dtCriacao;
    private LocalDateTime dtLeitura;
    private LocalDateTime dtExclusao;
    private Integer idProduto;
}
