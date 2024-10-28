package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.empresa.*;
import com.example.CalencareApi.service.EmpresaService;
import com.example.CalencareApi.service.EnderecoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService service;
    private final EnderecoService enderecoService;

    public EmpresaController(EmpresaService service, EnderecoService enderecoService) {
        this.service = service;
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EmpresaConsultaDto> cadastrar(@Valid @RequestBody EmpresaCriacaoDto empresaDto) {
        if (Objects.isNull(empresaDto)) {
            return ResponseEntity.status(400).build();
        }

        if (this.service.existeCnpj(empresaDto.getCnpj())) {
            return ResponseEntity.status(409).build();
        }

        EmpresaConsultaDto empresaCadastrada = this.service.cadastrarEmpresa(empresaDto);
        return ResponseEntity.status(201).body(empresaCadastrada);
    }

    @GetMapping("/cor")
    public ResponseEntity<List<String>> buscarEmpresasPorCor() {
        List<String> cores = new ArrayList<>();
        cores.add("#f0358f");
        cores.add("rgba(201, 85, 141, 0.15)");
        cores.add("#ea5889");
        return ResponseEntity.status(200).body(cores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaConsultaDto> buscarEmpresaPorId(@PathVariable Integer id) {
        if (!this.service.existeEmpresaPorId(id)) {
            return ResponseEntity.status(404).build();
        }

        EmpresaConsultaDto empresaDto = this.service.buscarEmpresaPorId(id);


        return ResponseEntity.status(200).body(empresaDto);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaConsultaDto>> listarEmpresas(){
        List<EmpresaConsultaDto> empresasDto = this.service.listarEmpresas();

        if(empresasDto.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(empresasDto);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<EmpresaConsultaDto>> pesquisarEmpresasPorRazaoSocial(@RequestParam String razaoSocial) {
        List<EmpresaConsultaDto> empresasDto = this.service.pesquisarEmpresaPorRazaoSocial(razaoSocial);

        if (empresasDto.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(empresasDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaConsultaDto> atualizarEmpresaPorId(@PathVariable Integer id, @Valid @RequestBody EmpresaAtualizacaoDto empresaDto) {
        if (Objects.isNull(empresaDto)) {
            return ResponseEntity.status(400).build();
        }

        if (!this.service.existeEmpresaPorId(id)) {
            return ResponseEntity.status(404).build();
        }

        EmpresaConsultaDto empresaAtualizadaDto = this.service.atualizarEmpresa(id, empresaDto);
        return ResponseEntity.status(200).body(empresaAtualizadaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmpresa(@PathVariable Integer id){
        if(!this.service.existeEmpresaPorId(id)){
            return ResponseEntity.status(404).build();
        }

       this.service.excluirEmpresaPorId(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/validarFila")
    public ResponseEntity<Void> validarFila() {
        this.service.validarFila();
        return ResponseEntity.status(200).build();
    }

}
