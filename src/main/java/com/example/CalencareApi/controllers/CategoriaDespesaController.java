package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaConsultaDto;
import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaCriacaoDto;
import com.example.CalencareApi.service.CategoriaDespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/categoria-despesa")
public class CategoriaDespesaController {

    @Autowired
    private CategoriaDespesaService categoriaDespesaService;

    @PostMapping()
    public ResponseEntity<CategoriaDespesaConsultaDto> cadastrarCategoriaDespesa(
         @Valid @RequestBody CategoriaDespesaCriacaoDto novaCategoriaDespesa) {

        if (Objects.isNull(novaCategoriaDespesa)) {
            return ResponseEntity.status(400).build();
        }
        if (this.categoriaDespesaService.existeNome(novaCategoriaDespesa.getNome())) {
            return ResponseEntity.status(409).build();
        }
        CategoriaDespesaConsultaDto categoriaDespesaCadastrada = this.categoriaDespesaService.cadastrar(novaCategoriaDespesa);
        return ResponseEntity.status(201).body(categoriaDespesaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDespesaConsultaDto>> listarCategoriaDespesa() {
        List<CategoriaDespesaConsultaDto> categoriasDespesa = this.categoriaDespesaService.Listar();
        if (categoriasDespesa.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(categoriasDespesa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDespesaConsultaDto> buscarCategoriaDespesaPorId(@PathVariable Integer id) {
        if (!this.categoriaDespesaService.existePorId(id)) {
            return ResponseEntity.status(404).build();
        }
        CategoriaDespesaConsultaDto categoriaDespesa = this.categoriaDespesaService.buscarPorId(id);
        return ResponseEntity.status(200).body(categoriaDespesa);
    }
}
