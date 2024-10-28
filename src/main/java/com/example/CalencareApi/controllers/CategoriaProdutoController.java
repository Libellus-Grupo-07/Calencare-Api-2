package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaConsultaDto;
import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaCriacaoDto;
import com.example.CalencareApi.dto.categoria.produto.CategoriaProdutoConsultaDto;
import com.example.CalencareApi.dto.categoria.produto.CategoriaProdutoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaProduto;
import com.example.CalencareApi.service.CategoriaProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController @RequestMapping("/categoria-produto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @PostMapping()
    public ResponseEntity<CategoriaProdutoConsultaDto> cadastrar(
            @Valid @RequestBody CategoriaProdutoCriacaoDto novaCategoriaProduto
    ) {
        if (Objects.isNull(novaCategoriaProduto)) {
            return ResponseEntity.status(400).build();
        }
        if (this.categoriaProdutoService.existeNome(novaCategoriaProduto.getNome())) {
            return ResponseEntity.status(409).build();
        }
        CategoriaProdutoConsultaDto categoriaDespesaCadastrada = this.categoriaProdutoService.cadastrar(novaCategoriaProduto);
        return ResponseEntity.status(201).body(categoriaDespesaCadastrada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoConsultaDto> buscarPorId(@PathVariable Integer id) {
        if (!this.categoriaProdutoService.existePorId(id)) {
            return ResponseEntity.status(404).build();
        }
        CategoriaProdutoConsultaDto categoriaProduto = this.categoriaProdutoService.buscarPorId(id);
        return ResponseEntity.status(200).body(categoriaProduto);
    }

    @GetMapping()
    public ResponseEntity<List<CategoriaProdutoConsultaDto>> listar() {
        List<CategoriaProdutoConsultaDto> categoriasProduto = this.categoriaProdutoService.listar();
        if (categoriasProduto.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(categoriasProduto);
    }

}
