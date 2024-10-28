package com.example.CalencareApi.dto.servicoPorFuncionario;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServicoPorFuncionarioCriacaoDto {
    private Integer idFuncionario;
    private Integer idServicoPreco;
    private LocalDateTime dtCriacao;
    private Integer bitStatus;
}
