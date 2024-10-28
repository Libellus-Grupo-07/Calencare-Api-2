package com.example.CalencareApi.models;

import com.example.CalencareApi.repository.IComissao;

import java.util.ArrayList;
import java.util.List;

public abstract class Funcionario extends Usuario implements IComissao {
    private String perfilAcesso;
    List<Agendamento> agendamentos = new ArrayList<>();

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public String getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(String perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "perfilAcesso='" + perfilAcesso + '\'' +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", sobreNome='" + sobrenome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
