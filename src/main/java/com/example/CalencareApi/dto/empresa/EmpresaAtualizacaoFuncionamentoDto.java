package com.example.CalencareApi.dto.empresa;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class EmpresaAtualizacaoFuncionamentoDto {
    @NotNull
    private LocalTime hrAbertura;
    @NotNull
    private LocalTime hrFechamento;

    public LocalTime getHrAbertura() {
        return hrAbertura;
    }

    public void setHrAbertura(LocalTime hrAbertura) {
        this.hrAbertura = hrAbertura;
    }

    public LocalTime getHrFechamento() {
        return hrFechamento;
    }

    public void setHrFechamento(LocalTime hrFechamento) {
        this.hrFechamento = hrFechamento;
    }
}
