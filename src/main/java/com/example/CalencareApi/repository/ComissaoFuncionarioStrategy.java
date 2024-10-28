package com.example.CalencareApi.repository;
import com.example.CalencareApi.models.Funcionario;

public class ComissaoFuncionarioStrategy implements IComissao<Funcionario> {
    @Override
    public Double calcularComissao(Funcionario f) {
        Double comissaoTotal = 0.0;
        for (int i = 0; i < f.getAgendamentos().size(); i++) {
            comissaoTotal += f.getAgendamentos().get(i).getServico().getvalor();
        }
        return comissaoTotal;
    }
}
