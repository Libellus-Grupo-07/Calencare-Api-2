package com.example.CalencareApi.dto.validade.movimentacao;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovimentacaoValidadeCriacaoDto {
    @NotNull
    private Integer tipoMovimentacao;
    @NotNull @Positive
    private Integer quantidade;
    @NotNull
    private Integer idValidade;
}