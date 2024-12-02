package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.MovimentosConsultaDto;
import com.example.CalencareApi.service.AgendamentoService;
import com.example.CalencareApi.service.DespesaService;
import com.example.CalencareApi.service.FinancasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/financas")
public class FinancasController {

    @Autowired FinancasService financasService;
    @Autowired AgendamentoService agendamentoService;
    @Autowired DespesaService despesaService;

    @GetMapping("/{empresa}/{mes}/{ano}")
    public List<MovimentosConsultaDto> getMovimentosMes(@PathVariable Integer empresa,@PathVariable Integer mes,@PathVariable Integer ano) {
        return financasService.getMovimentosMes(empresa, Month.of(mes), Year.of(ano));
    }

    @GetMapping("/kpi/receitas-mes/{empresa}/{mes}/{ano}")
    public Double getReceitasMes(@PathVariable Integer empresa,@PathVariable Integer mes,@PathVariable Integer ano) {
        return agendamentoService.getReceitasMes(empresa, Month.of(mes), Year.of(ano));
    }

    @GetMapping("/kpi/comissao-mes/{empresa}/{mes}/{ano}")
    public Double getComissaoMes(@PathVariable Integer empresa,@PathVariable Integer mes,@PathVariable Integer ano) {
        return agendamentoService.getComissaoMes(empresa, Month.of(mes), Year.of(ano));
    }

    @GetMapping("/kpi/lucro-mes/{empresa}/{mes}/{ano}")
    public Double getLucroMes(@PathVariable Integer empresa,@PathVariable Integer mes,@PathVariable Integer ano) {
        return agendamentoService.getLucroMes(empresa, Month.of(mes), Year.of(ano));
    }

    @GetMapping("/kpi/despesa-mes/{idEmpresa}/{mes}/{ano}")
    public ResponseEntity<Double> calcularDespesaTotalMes(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer mes,
            @PathVariable Integer ano) {
        Double total = despesaService.calcularDespesaTotalMes(idEmpresa, Month.of(mes), Year.of(ano));
        return ResponseEntity.ok(total);
    }
}
