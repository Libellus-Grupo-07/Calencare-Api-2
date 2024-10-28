package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.servico.ServicoConsultaDto;
import com.example.CalencareApi.dto.servico.ServicoCriacaoDto;
import com.example.CalencareApi.entity.Servico;
import com.example.CalencareApi.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    @Autowired
    private ServicoService servicoService;

    @PostMapping("/{id}")
    public ResponseEntity<Servico> cadastrar(@RequestBody ServicoCriacaoDto servico, @PathVariable int id) {
        Servico servicoCadastrado = servicoService.cadastrar(servico, id);
        if (servicoCadastrado == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(201).body(servicoCadastrado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoConsultaDto> buscarPorId(@PathVariable int id) {
        ServicoConsultaDto servico = servicoService.buscarPorId(id);
        if (servico == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(201).body(servico);
    }

    @GetMapping
    public ResponseEntity<List<ServicoConsultaDto>> buscarTodos() {
        List<ServicoConsultaDto> servicos = servicoService.buscarTodos();
        if (servicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(servicos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoConsultaDto> atualizar(@RequestBody ServicoCriacaoDto servico, @PathVariable int id) {
        ServicoConsultaDto servicoAtualizado = servicoService.alterar(id, servico);
        if (servicoAtualizado == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(servicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        boolean deletado = servicoService.deletar(id);
        if (!deletado) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
