package com.example.CalencareApi.entity;

import com.example.CalencareApi.interfaces.AgendamentoObserver;
import com.example.CalencareApi.service.EmailService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
public class Funcionario implements AgendamentoObserver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String perfilAcesso;
    private Integer bitStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;
    @CreationTimestamp
    private LocalDateTime dtCriacao;

    @Override
    public void notificar(EmailService emailService, Agendamento agendamento){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        emailService.enviarEmailTexto(this.email,
                "FUNCIONARIO - Calencare | Agendamento realizado",
                """
                        Olá!
                        O agendamento para %s foi realizado com sucesso.
                        
                        • Profissional: %s
                        • Data: %s às %s horas
                        • Serviço: %s
                        • Valor: R$ %.2f
                        
                        Calencare
                        Organizando o seu negócio sempre que você quiser!"""
                                .formatted(agendamento.getCliente().getNome(),
                                        agendamento.getFuncionario().getNome(),
                                        agendamento.getDia().format(formatter),
                                        agendamento.getHorario(),
                                        agendamento.getServicoPreco().getServico().getNome(),
                                        agendamento.getServicoPreco().getPreco()));
    }

}
