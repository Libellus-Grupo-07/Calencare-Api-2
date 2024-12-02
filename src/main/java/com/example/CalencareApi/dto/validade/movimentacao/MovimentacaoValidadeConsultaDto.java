package com.example.CalencareApi.dto.validade.movimentacao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MovimentacaoValidadeConsultaDto {
    private Integer id;
    private Integer tipoMovimentacao;
    private Integer quantidade;
    private LocalDateTime dtCriacao;
    private Integer idValidade;
    private Integer idProduto;
}
