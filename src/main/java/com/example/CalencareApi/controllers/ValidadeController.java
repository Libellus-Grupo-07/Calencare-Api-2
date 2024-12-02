package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.validade.ValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.ValidadeCriacaoDto;
import com.example.CalencareApi.service.ValidadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/validade")
public class ValidadeController {

    public final ValidadeService validadeService;

    public ValidadeController(ValidadeService validadeService) {
        this.validadeService = validadeService;
    }

    @PostMapping
    public ResponseEntity<ValidadeConsultaDto> cadastrar(@Valid @RequestBody ValidadeCriacaoDto validadeCriacaoDto) {
        ValidadeConsultaDto validadeConsultaDto = validadeService.cadastrar(validadeCriacaoDto);
        if (validadeConsultaDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(validadeConsultaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValidadeConsultaDto> buscarPorId(@PathVariable Integer id) {
        ValidadeConsultaDto validadeConsultaDto = validadeService.buscarPorId(id);
        if (validadeConsultaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(validadeConsultaDto);
    }

    @GetMapping("/produto/{idProduto}")
    public ResponseEntity<List<ValidadeConsultaDto>> buscarPorIdProduto(@PathVariable Integer idProduto) {
        if (validadeService.buscarPorIdProduto(idProduto).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(validadeService.buscarPorIdProduto(idProduto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletar(@PathVariable Integer id) {
        Boolean deletado = validadeService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<ValidadeConsultaDto>> buscarPorEmpresaId(@PathVariable Integer idEmpresa) {
        if (validadeService.buscarPorEmpresaId(idEmpresa).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(validadeService.buscarPorEmpresaId(idEmpresa));
    }

}
