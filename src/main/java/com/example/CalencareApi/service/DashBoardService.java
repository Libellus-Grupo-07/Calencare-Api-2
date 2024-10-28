package com.example.CalencareApi.service;

import com.example.CalencareApi.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final AgendamentoRepository dashBoardRepository;


    public Optional<Map<String, Integer>> getAgendamentoStats(Integer empresaId, LocalDate date) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("TotalAgendamentos", dashBoardRepository.getTotalAgendamentos(empresaId, date));
        stats.put("Finalizados", dashBoardRepository.getAgendamentosStatusFinalizado(empresaId, date));
        stats.put("Cancelados", dashBoardRepository.getAgendamentosStatusCancelado(empresaId, date));
        stats.put("Pendentes", dashBoardRepository.getAgendamentosStatusPendente(empresaId, date));
        return Optional.ofNullable(stats.isEmpty() ? null : stats);
    }

    public Optional<Integer> getAgendamentosConfirmadosDoDia(Integer empresaId, LocalDate date) {
        Integer result = dashBoardRepository.getAgendamentosConfirmadosDoDia(empresaId, date);
        return Optional.ofNullable(result);
    }

    public Optional<Map<String, Object>> getLucroTotalDoDia(Integer empresaId, LocalDate date) {
        Map<String, Object> result = new HashMap<>();
        result.put("LucroTotalDoDia", dashBoardRepository.getLucroTotalDoDia(empresaId, date));
        result.put("AgendamentosConfirmados", getAgendamentosConfirmadosDoDia(empresaId, date));
        return Optional.ofNullable(result.isEmpty() ? null : result);
    }

    public Optional<List<Map<String, Object>>> getAgendamentosDoDiaPorProfissional(Integer empresaId, LocalDate date) {
        List<Map<String, Object>> result = dashBoardRepository.getAgendamentosDoDiaPorProfissional(empresaId, date);
        return Optional.ofNullable(result.isEmpty() ? null : result);
    }

    public Optional<List<Map<String, Object>>> getServicoMaisProcuradoERentabilidadeDoDia(Integer empresaId, LocalDate date) {
        List<Map<String, Object>> result = dashBoardRepository.getServicoMaisProcuradoERentabilidadeDoDia(empresaId, date, PageRequest.of(0, 1));
        return Optional.ofNullable(result.isEmpty() ? null : result);
    }


    public List<Map<String, Object>> getTop3Servicos(Integer empresaId, LocalDate date, Pageable pageable) {
        return dashBoardRepository.getTop3Servicos(empresaId, date, pageable);
    }

    public List<Map<String, Object>> getTop3Profissionais(Integer empresaId, LocalDate date, Pageable pageable) {
        return dashBoardRepository.getTop3Profissionais(empresaId, date, pageable);
    }

    public List<Map<String, Object>> getTop3Clientes(Integer empresaId, LocalDate date, Pageable pageable) {
        return dashBoardRepository.getTop3Clientes(empresaId, date, pageable);
    }

    public Optional<List<Map<String, Object>>> getAgendamentosPorCategoria(Integer empresaId, LocalDate date) {
        return Optional.ofNullable(dashBoardRepository.getAgendamentosPorCategoria(empresaId, date));
    }

}
