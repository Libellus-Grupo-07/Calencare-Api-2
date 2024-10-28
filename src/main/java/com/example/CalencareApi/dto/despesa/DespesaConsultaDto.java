package com.example.CalencareApi.dto.despesa;

import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.entity.Empresa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DespesaConsultaDto {
    private Integer id;
    private String nome;
    private String observacao;
    private Double valor;
    private String formaPagamento;
    private LocalDateTime dtPagamento;
    private Integer empresaId;
    private String categoriaDespesaNome;
    private Integer categoriaDespesaId;
}
