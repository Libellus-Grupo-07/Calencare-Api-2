package com.example.CalencareApi.models;

public class FuncionarioOperacao extends Funcionario{

    @Override
    public Double calcularComissao(Object objeto) {
        return null;
    }

    @Override
    public String toString() {
        return "FuncionarioOperacao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
