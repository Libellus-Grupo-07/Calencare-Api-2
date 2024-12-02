package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.DashSemanaValorDto;
import com.example.CalencareApi.service.DashFinancasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/dash-financas")
public class DashFinancasController {

    private final DashFinancasService service;

    public DashFinancasController(DashFinancasService service) {
        this.service = service;
    }

    // testando dados para dash
    @GetMapping("/semana/despesa/{idEmpresa}/{ano}/{mes}")
    public ResponseEntity<List<DashSemanaValorDto>> getTotalDespesasPorSemana(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer ano,
            @PathVariable Integer mes) {
        List<DashSemanaValorDto> despesas = service.getDespesaSemanal(idEmpresa, Month.of(mes), Year.of(ano));
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/semana/agendamentos-receita/{idEmpresa}/{ano}/{mes}")
    public ResponseEntity<List<DashSemanaValorDto>> getTotalAgendamentoReceitaPorSemana(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer ano,
            @PathVariable Integer mes) {
        List<DashSemanaValorDto> despesas = service.getAgendamentoValorSemanal(idEmpresa, Month.of(mes), Year.of(ano));
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/semana/agendamentos-lucro/{idEmpresa}/{ano}/{mes}")
    public ResponseEntity<List<DashSemanaValorDto>> getTotalAgendamentoLucroPorSemana(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer ano,
            @PathVariable Integer mes) {
        List<DashSemanaValorDto> despesas = service.getAgendamentoLucroSemanal(idEmpresa, Month.of(mes), Year.of(ano));
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/teste-semana-ano/{ano}/{mes}")
    public ResponseEntity<List<Integer>> getTesteSemanaAno(
            @PathVariable Integer ano,
            @PathVariable Integer mes) {
        List<Integer> despesas = service.buscarSemanasDoMes(mes, ano);
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/dash/{empresa}/{ano}/{mes}")
    public ResponseEntity<DashSemanaValorDto[][]> getDash(
            @PathVariable Integer empresa,
            @PathVariable Integer ano,
            @PathVariable Integer mes) {
        DashSemanaValorDto[][] despesas = service.getDadosDashboard(empresa, Month.of(mes), Year.of(ano));
        return ResponseEntity.ok(despesas);
    }
}
