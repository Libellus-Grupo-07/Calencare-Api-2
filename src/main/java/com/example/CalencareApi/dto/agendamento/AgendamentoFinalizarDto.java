package com.example.CalencareApi.dto.agendamento;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgendamentoFinalizarDto {
    @NotBlank private String metodoPagamento;
}
