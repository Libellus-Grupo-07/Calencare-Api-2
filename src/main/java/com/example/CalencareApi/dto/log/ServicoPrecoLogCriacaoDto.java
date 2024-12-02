package com.example.CalencareApi.dto.log;

import lombok.Data;

@Data
public class ServicoPrecoLogCriacaoDto {
    private Double precoAtual;
    private Double precoAnterior;
    private Integer duracaoAtual;
    private Integer duracaoAnterior;
    private Double comissaoAtual;
    private Double comissaoAnterior;
    private Integer servicoPrecoId;
}
