package com.example.CalencareApi.dto.horarioFuncionamento;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class HorarioFuncionamentoCriacaoDto {
    @NotNull
    private String diaSemana;
    @NotNull
    private Integer codDiaSemana;
    @NotNull
    private LocalTime inicio;
    @NotNull
    private LocalTime fim;
    @NotNull
    private Integer status;
    @NotNull
    private Integer empresaId;
}
