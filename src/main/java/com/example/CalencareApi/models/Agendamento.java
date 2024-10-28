package com.example.CalencareApi.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Agendamento {
    private Integer id;
    private LocalTime horario;
    private LocalDate data;
    private Servico servico;
    private Cliente cliente;
    private Funcionario funcionario;
    private List<Mensagem> mensagens =new ArrayList<>();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "AgendamentoController{" +
                "id=" + id +
                ", horario=" + horario +
                ", data=" + data +
                ", servico=" + servico +
                ", cliente=" + cliente +
                ", funcionario=" + funcionario +
                '}';
    }
}
