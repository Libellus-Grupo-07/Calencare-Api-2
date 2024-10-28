package com.example.CalencareApi.dto.validade;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ValidadeCriacaoDto {
    String descricao;
    @FutureOrPresent
    LocalDateTime dtValidade;
    @NotNull
    LocalDateTime dtCriacao;
    @NotNull
    Integer idProduto;
}
