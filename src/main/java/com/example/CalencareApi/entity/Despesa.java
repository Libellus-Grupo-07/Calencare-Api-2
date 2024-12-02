package com.example.CalencareApi.entity;

import com.example.CalencareApi.dto.DashSemanaValorDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SqlResultSetMapping(
        name = "DespesaDashSemanaValorDtoMapping",
        classes = @ConstructorResult(
                targetClass = DashSemanaValorDto.class,
                columns = {
                        @ColumnResult(name = "semana", type = Integer.class),
                        @ColumnResult(name = "valor", type = Double.class)
                }
        )
)
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String observacao;
    private Double valor;
    private String formaPagamento;
    private LocalDateTime dtPagamento;
    private LocalDateTime dtCriacao;
    private Integer bitStatus;
    @ManyToOne
    private Empresa empresa;
    @ManyToOne
    private CategoriaDespesa categoriaDespesa;



}