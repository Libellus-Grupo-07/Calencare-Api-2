package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
public class HorarioFuncionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String diaSemana;
    private Integer codDiaSemana;
    private LocalTime inicio;
    private LocalTime fim;
    private Integer status;
    @ManyToOne
    private Empresa empresa;
}
