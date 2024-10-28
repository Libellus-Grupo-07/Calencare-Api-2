package com.example.CalencareApi.controllers;

import com.example.CalencareApi.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;


    @GetMapping("/stats/{empresaId}/{date}")
    public ResponseEntity<Map<String, Integer>> getAgendamentoStats(@PathVariable Integer empresaId, @PathVariable LocalDate date) {
        return dashBoardService.getAgendamentoStats(empresaId, date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/confirmados/{empresaId}/{date}")
    public ResponseEntity<Integer> getAgendamentosConfirmadosDoDia(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(dashBoardService.getAgendamentosConfirmadosDoDia(empresaId, date).orElse(0));
    }

    @GetMapping("/lucro/{empresaId}/{date}")
    public ResponseEntity<Map<String, Object>> getLucroTotalDoDia(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(dashBoardService.getLucroTotalDoDia(empresaId, date).orElse(Collections.emptyMap()));
    }

    @GetMapping("/profissional/{empresaId}/{date}")
    public ResponseEntity<List<Map<String, Object>>> getAgendamentosDoDiaPorProfissional(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(dashBoardService.getAgendamentosDoDiaPorProfissional(empresaId, date).orElse(Collections.emptyList()));
    }

    @GetMapping("/rentabilidade/{empresaId}/{date}")
    public ResponseEntity<List<Map<String, Object>>> getServicoMaisProcuradoERentabilidadeDoDia(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(dashBoardService.getServicoMaisProcuradoERentabilidadeDoDia(empresaId, date).orElse(Collections.emptyList()));
    }

    
    @GetMapping("/top3-servicos/{empresaId}/{date}")
    public ResponseEntity<List<Map<String, Object>>> getTop3Servicos(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Map<String, Object>> top3Servicos = dashBoardService.getTop3Servicos(empresaId, date, PageRequest.of(0, 3));
        return ResponseEntity.ok(top3Servicos);
    }

    @GetMapping("/top3-profissionais/{empresaId}/{date}")
    public ResponseEntity<List<Map<String, Object>>> getTop3Profissionais(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Map<String, Object>> top3Profissionais = dashBoardService.getTop3Profissionais(empresaId, date, PageRequest.of(0, 3));
        return ResponseEntity.ok(top3Profissionais);
    }

    @GetMapping("/top3-clientes/{empresaId}/{date}")
    public ResponseEntity<List<Map<String, Object>>> getTop3Clientes(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Map<String, Object>> top3Clientes = dashBoardService.getTop3Clientes(empresaId, date, PageRequest.of(0, 3));
        return ResponseEntity.ok(top3Clientes);
    }

    @GetMapping("/categoria/{empresaId}/{date}")
    public ResponseEntity<List<Map<String, Object>>> getAgendamentosPorCategoria(@PathVariable Integer empresaId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Map<String, Object>> agendamentosPorCategoria = dashBoardService.getAgendamentosPorCategoria(empresaId, date).orElse(Collections.emptyList());
        return ResponseEntity.ok(agendamentosPorCategoria);
    }
}