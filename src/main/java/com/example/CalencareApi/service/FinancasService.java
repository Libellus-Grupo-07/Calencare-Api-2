package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.MovimentosConsultaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Month;
import java.time.Year;
import java.util.*;

@Service
public class FinancasService {

    @Autowired AgendamentoService agendamentoService;
    @Autowired DespesaService despesaService;

    public List<MovimentosConsultaDto> getMovimentosMes(Integer empresa, Month mes, Year ano) {
        List<MovimentosConsultaDto> movimentos = new ArrayList<>();
        List<Map<String, Object>> despesas = despesaService.getListaDespesaDiaria(empresa,  mes, ano);
        List<Map<String, Object>>  agendamentos = agendamentoService.getReceitaDiaria(empresa, mes, ano);

        for (Map<String, Object> agendamento : agendamentos) {
            MovimentosConsultaDto movimento = new MovimentosConsultaDto();
            movimento.setData((Date) agendamento.get("data"));
            movimento.setDescricao("Agendamentos");
            movimento.setTotal((Double) agendamento.get("total"));
            movimentos.add(movimento);
        }

        for (Map<String, Object> despesa : despesas) {
            MovimentosConsultaDto movimento = new MovimentosConsultaDto();
            movimento.setData((Date) despesa.get("data"));
            movimento.setDescricao("Despesas");
            movimento.setTotal((Double) despesa.get("total"));
            movimentos.add(movimento);
        }
        movimentos.sort(Comparator.comparing(MovimentosConsultaDto::getData).reversed());
        return movimentos;
    }

}
