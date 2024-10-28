package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ServicoPorFuncionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    private LocalDateTime dtAdd;
    private Integer bitStatus;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private ServicoPreco servicoPreco;
}