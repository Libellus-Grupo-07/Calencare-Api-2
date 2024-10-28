package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.MovimentosConsultaDto;
import com.example.CalencareApi.service.FinancasService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{empresa}/{mes}/{ano}")
    public List<MovimentosConsultaDto> getMovimentosMes(@PathVariable Integer empresa,@PathVariable Integer mes,@PathVariable Integer ano) {
        return financasService.getMovimentosMes(empresa, Month.of(mes), Year.of(ano));
    }
}
