package com.example.CalencareApi.repository;

import com.example.CalencareApi.models.Servico;

public class ComissaoServicoStrategy implements IComissao<Servico>{

    @Override
    public Double calcularComissao(Servico s) {
        return s.getvalor() * (s.getPorcentComissao() / 100);
    }
}