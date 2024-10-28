package com.example.CalencareApi.dto.agendamento;

import com.example.CalencareApi.interfaces.ValidaStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AgendamentoConsultaDto implements ValidaStatus {
    private int id;
    private LocalDateTime dtHora;
    private LocalDate dia;
    private LocalTime horario;
    private String nomeFuncionario;
    private String nomeCliente;
    private String nomeServico;
    private Double preco;
    private Double comissao;
    private Integer status;
    private Integer duracaoServico;
    private String metodoPagamento;
    private Double valorServico;
    private String telefoneCliente;
    private Integer funcionarioId;
    private Integer clienteId;
    private Integer servicoPrecoId;

    public String getDescricaoStatus() {
        return validaStatus(this.status);
    }

    public LocalTime getHorarioFinalizacao(){
        return getDtHora().toLocalTime().plusMinutes(duracaoServico);
    }
}


