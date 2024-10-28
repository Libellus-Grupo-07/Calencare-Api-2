package com.example.CalencareApi.dto.servicoPreco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServicoPrecoCriacaoDto {
    @NotBlank
    private String nome; // entidade Servico
    @NotBlank
    private String descricao;
    @NotNull @Positive
    private Double preco;
    @NotNull
    private Integer duracao;
    @NotBlank
    private String categoria;
    @NotNull @Positive
    private Double comissao;
    @NotNull
    private Integer bitStatus;
    private Integer empresaId; // settado via Usuario
    private Integer servicoId; // retornar fk ao cadastrar ServicoPreco
}

