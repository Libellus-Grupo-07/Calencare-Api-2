package com.example.CalencareApi.service;

import com.example.CalencareApi.entity.Agendamento;
import com.example.CalencareApi.repository.AgendamentoRepository;
import com.example.CalencareApi.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilFuncionarioService {

    private final AgendamentoRepository perfilFuncionarioRepository;
    private final FuncionarioRepository funcionarioRepository;

    public String retornarNomeFuncionario(Integer funcionarioId) {
        if (funcionarioRepository.findById(funcionarioId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Funcionário com id %d não encontrado", funcionarioId));
        }
        return funcionarioRepository.findById(funcionarioId).get().getNome();
    }

    public List<Agendamento> getAgendamentosDoMes(Integer funcionarioId, Month month, Year year) {
        LocalDateTime startOfMonth = LocalDateTime.of(year.getValue(), month.getValue(), 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return perfilFuncionarioRepository.findByFuncionarioIdAndDtHoraBetweenOrderByDtHora(funcionarioId, startOfMonth, endOfMonth);
    }

    public Integer getQuantidadeAgendamentosDoMes(Integer funcionarioId, Month month, Year year) {
        LocalDateTime startOfMonth = LocalDateTime.of(year.getValue(), month.getValue(), 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return perfilFuncionarioRepository.countByFuncionarioIdAndDtHoraBetweenAndBitStatus(funcionarioId, startOfMonth, endOfMonth);
    }

    public Integer getQuantidadeClientesAtendidosDoMes(Integer funcionarioId, Month month, Year year) {
        LocalDateTime startOfMonth = LocalDateTime.of(year.getValue(), month.getValue(), 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return perfilFuncionarioRepository.countDistinctClienteByFuncionarioIdAndDtHoraBetween(funcionarioId, startOfMonth, endOfMonth);
    }


    public Double getValorComissaoDoMes(Integer funcionarioId, Month month, Year year) {
        LocalDateTime startOfMonth = LocalDateTime.of(year.getValue(), month.getValue(), 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return perfilFuncionarioRepository.sumComissaoByFuncionarioIdAndDtHoraBetween(funcionarioId, startOfMonth, endOfMonth);
    }
}
