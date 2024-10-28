package com.example.CalencareApi.dto.servicoPreco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ServicoPrecoAtualizacaoComissaoDto {
    @NotNull
    @Positive
    private Double comissao;

    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }
}
