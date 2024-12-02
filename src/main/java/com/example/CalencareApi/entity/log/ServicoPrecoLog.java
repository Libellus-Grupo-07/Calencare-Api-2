package com.example.CalencareApi.entity.log;

import com.example.CalencareApi.entity.ServicoPreco;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Entity
public class ServicoPrecoLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dtCriacao;
    private Double precoAtual;
    private Double precoAnterior;
    private Integer duracaoAtual;
    private Integer duracaoAnterior;
    private Double comissaoAtual;
    private Double comissaoAnterior;
    @ManyToOne
    private ServicoPreco servicoPreco;
}
