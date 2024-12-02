package com.example.CalencareApi.dto.log;

import lombok.Data;

@Data
public class ServicoPrecoLogConsultaDto {
    private Integer id;
    private String dtCriacao;
    private Double precoAtual;
    private Double precoAnterior;
    private Integer duracaoAtual;
    private Integer duracaoAnterior;
    private Double comissaoAtual;
    private Double comissaoAnterior;
    private Integer servicoPrecoId;
}
