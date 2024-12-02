package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
//    private String cep;
    private String razaoSocial;
    private String emailPrincipal;
    private String telefonePrincipal;
    private Integer intervaloAtendimento;
    @CreationTimestamp
    private LocalDateTime dtCriacao;
    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;
    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    private List<HorarioFuncionamento> horarioFuncionamentos;
}
