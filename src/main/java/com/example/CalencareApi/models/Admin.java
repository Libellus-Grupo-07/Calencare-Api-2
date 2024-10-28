package com.example.CalencareApi.models;

public class Admin extends Funcionario{

    @Override
    public Double calcularComissao(Object objeto) {
        return null;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
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
