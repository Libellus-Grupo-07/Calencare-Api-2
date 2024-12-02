package com.example.CalencareApi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NotificacaoEstoqueCriacaoDto {
    @NotNull
    private Integer id;
    @NotNull
    private String nomeProduto;
    @NotNull
    private String nivelEstoque;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Integer lido;
    @NotNull
    private Integer excluido;
    @FutureOrPresent
    private LocalDateTime dtCriacao;
    private LocalDateTime dtLeitura;
    private LocalDateTime dtExclusao;
    private Integer idProduto;
}
