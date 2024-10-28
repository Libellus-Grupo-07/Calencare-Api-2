package com.example.CalencareApi.dto.agendamento;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Data
public class AgendamentoAtualizarDto {
    @FutureOrPresent
    @NotNull
    private LocalDateTime dtHora;
    @FutureOrPresent
    @NotNull
    private LocalDate dia;
    @FutureOrPresent
    @NotNull
    private LocalTime horario;
    @Min(1) @Max(4) @NotNull
    private Integer bitStatus;
    @NotNull
    private Integer fkCliente;
    @NotNull
    private Integer fkFuncionario;
    @NotNull
    private Integer fkServicoPreco;
}
