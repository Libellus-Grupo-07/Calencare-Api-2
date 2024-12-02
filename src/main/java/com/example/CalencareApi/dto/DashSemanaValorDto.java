package com.example.CalencareApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DashSemanaValorDto {
    private Integer semana;
    private Double valor;

    public DashSemanaValorDto(Integer semana, Double valor) {
        this.semana = semana;
        this.valor = valor;
    }

    public DashSemanaValorDto() {
    }
}
