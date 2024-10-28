package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoAtualizarDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoConsultaDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoCriacaoDto;
import com.example.CalencareApi.service.CategoriaServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria-servico")
public class CategoriaServicoController {

    @Autowired
    private CategoriaServicoService categoriaServicoService;

    @PostMapping
    public ResponseEntity<CategoriaServicoConsultaDto> cadastrar(@RequestBody @Valid CategoriaServicoCriacaoDto novaCategoria) {
        if (novaCategoria == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.categoriaServicoService.cadastrar(novaCategoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaServicoConsultaDto>> buscarTodos() {
        List<CategoriaServicoConsultaDto> categorias = this.categoriaServicoService.buscarTodos();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaServicoConsultaDto> buscarPorId(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        CategoriaServicoConsultaDto categoria = this.categoriaServicoService.buscarPorId(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaServicoConsultaDto> alterar(
            @PathVariable Integer id,
            @RequestBody @Valid CategoriaServicoAtualizarDto categoriaAtualizada) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        CategoriaServicoConsultaDto categoriaAlterada = this.categoriaServicoService.alterar(id, categoriaAtualizada);
        if (categoriaAlterada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaAlterada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (this.categoriaServicoService.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        this.categoriaServicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
