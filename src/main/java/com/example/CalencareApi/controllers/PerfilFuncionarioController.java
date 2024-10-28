package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.agendamento.AgendamentoConsultaDto;
import com.example.CalencareApi.entity.Agendamento;
import com.example.CalencareApi.mapper.AgendamentoMapper;
import com.example.CalencareApi.service.PerfilFuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/perfilFuncionario")
public class PerfilFuncionarioController {

    private final PerfilFuncionarioService perfilFuncionarioService;

    @GetMapping("/agendamentos/{funcionarioId}/{year}/{month}")
    public ResponseEntity<List<AgendamentoConsultaDto>> getAgendamentosDoMes(@PathVariable Integer funcionarioId, @PathVariable int year, @PathVariable int month) {
        List<Agendamento> agendamentos = perfilFuncionarioService.getAgendamentosDoMes(funcionarioId, Month.of(month), Year.of(year));
        List<AgendamentoConsultaDto> agendamentoConsultaDtos = AgendamentoMapper.toDto(agendamentos);
        return ResponseEntity.ok(agendamentoConsultaDtos);
    }

    @GetMapping("/quantidadeAgendamentos/{funcionarioId}/{year}/{month}")
    public ResponseEntity<Integer> getQuantidadeAgendamentosDoMes(@PathVariable Integer funcionarioId, @PathVariable int year, @PathVariable int month) {
        Integer quantidadeAgendamentos = perfilFuncionarioService.getQuantidadeAgendamentosDoMes(funcionarioId, Month.of(month), Year.of(year));
        return ResponseEntity.ok(quantidadeAgendamentos);
    }

    @GetMapping("/quantidadeClientesAtendidos/{funcionarioId}/{year}/{month}")
    public ResponseEntity<Integer> getQuantidadeClientesAtendidosDoMes(@PathVariable Integer funcionarioId, @PathVariable int year, @PathVariable int month) {
        Integer quantidadeClientesAtendidos = perfilFuncionarioService.getQuantidadeClientesAtendidosDoMes(funcionarioId, Month.of(month), Year.of(year));
        return ResponseEntity.ok(quantidadeClientesAtendidos);
    }

    @GetMapping("/valorComissao/{funcionarioId}/{year}/{month}")
    public ResponseEntity<Double> getValorComissaoDoMes(@PathVariable Integer funcionarioId, @PathVariable int year, @PathVariable int month) {
        Double valorComissao = perfilFuncionarioService.getValorComissaoDoMes(funcionarioId, Month.of(month), Year.of(year));
        return ResponseEntity.ok(valorComissao);
    }

    @GetMapping("/nomeFuncionario/{funcionarioId}")
    public ResponseEntity<String> getNomeFuncionario(@PathVariable Integer funcionarioId) {
        String nomeFuncionario = perfilFuncionarioService.retornarNomeFuncionario(funcionarioId);
        return ResponseEntity.ok(nomeFuncionario);
    }
}
