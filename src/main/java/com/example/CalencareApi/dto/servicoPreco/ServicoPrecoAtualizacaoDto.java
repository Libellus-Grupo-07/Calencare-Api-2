package com.example.CalencareApi.dto.servicoPreco;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ServicoPrecoAtualizacaoDto {
    private String nome; // entidade Servico
    private String descricao;
    @Positive private Double preco;
    @Positive private Integer duracao;
    @Positive private Double comissao;
    @PositiveOrZero private Integer bitStatus;
    private String categoria;
    private Integer servicoId; // settado via Usuario
}