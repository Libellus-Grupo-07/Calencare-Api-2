package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.horarioFuncionamento.HorarioFuncionamentoConsultaDto;
import com.example.CalencareApi.dto.horarioFuncionamento.HorarioFuncionamentoCriacaoDto;
import com.example.CalencareApi.service.HorarioFuncionamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios-funcionamento")
public class HorarioFuncionamentoController {

    @Autowired
    private HorarioFuncionamentoService service;

    @PostMapping
    public ResponseEntity<HorarioFuncionamentoConsultaDto> createHorario(@Valid @RequestBody HorarioFuncionamentoCriacaoDto dto) {
        HorarioFuncionamentoConsultaDto savedDto = service.criarHorarioFuncionamento(dto);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping
    public ResponseEntity<List<HorarioFuncionamentoConsultaDto>> getAllHorarios() {
        List<HorarioFuncionamentoConsultaDto> dtos = service.listar();
        return ResponseEntity.status(200).body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioFuncionamentoConsultaDto> getHorarioById(@PathVariable Integer id) {
        HorarioFuncionamentoConsultaDto dto = service.horarioPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioFuncionamentoConsultaDto> updateHorario(@PathVariable Integer id, @Valid @RequestBody HorarioFuncionamentoCriacaoDto dto) {
            HorarioFuncionamentoConsultaDto updatedDto = service.atualizarHorarioFuncionamento(id, dto);
            return ResponseEntity.ok(updatedDto);
    }
}