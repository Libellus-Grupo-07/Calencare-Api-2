package com.example.CalencareApi.dto.funcionario;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FuncionarioAtualizacaoStatusDto {
    @PositiveOrZero private Integer bitStatus;
}
