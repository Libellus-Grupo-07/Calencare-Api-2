package com.example.CalencareApi.dto.agendamento;

import com.example.CalencareApi.entity.Cliente;
import com.example.CalencareApi.entity.Funcionario;
import com.example.CalencareApi.entity.ServicoPreco;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AgendamentoCriacaoDto {
    @FutureOrPresent @NotNull
    private LocalDateTime dtHora;
    @FutureOrPresent @NotNull
    private LocalDate dia;
    @FutureOrPresent @NotNull
    private LocalTime horario;
    @Min(1) @Max(4) @NotNull
    private Integer bitStatus;
    @NotNull
    private Cliente cliente;
    @NotNull
    private Funcionario funcionario;
    @NotNull
    private ServicoPreco servicoPreco;

}
