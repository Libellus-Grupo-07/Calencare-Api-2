package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.funcionario.*;
import com.example.CalencareApi.service.FuncionarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<FuncionarioConsultaDto> criar(@RequestBody @Valid FuncionarioCriacaoDto funcionarioCriacaoDto) {
        FuncionarioConsultaDto funcionarioCriado = this.funcionarioService.criar(funcionarioCriacaoDto);
        return ResponseEntity.status(201).body(funcionarioCriado);
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDto> login(@RequestBody FuncionarioLoginDto funcionarioLoginDto) {
        FuncionarioTokenDto funcionarioTokenDto = this.funcionarioService.autenticar(funcionarioLoginDto);

        return ResponseEntity.status(200).body(funcionarioTokenDto);
    }


    @GetMapping
    public ResponseEntity<List<FuncionarioConsultaDto>> listar() {
        List<FuncionarioConsultaDto> funcionarios = funcionarioService.listar();
        return ResponseEntity.status(200).body(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioConsultaDto> buscarPorId(@PathVariable int id) {
        FuncionarioConsultaDto funcionario = funcionarioService.buscarPorId(id);
        return ResponseEntity.status(200).body(funcionario);
    }


    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioConsultaDto> atualizar(@RequestBody @Valid FuncionarioAtualizacaoDto funcionarioAtualizado, @PathVariable int id) {
        FuncionarioConsultaDto funcionario = funcionarioService.alterar(funcionarioAtualizado, id);
        return ResponseEntity.status(200).body(funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
         funcionarioService.deletar(id);
        return ResponseEntity.status(204).build();
    }


    @GetMapping("/csv")
    public ResponseEntity<byte[]> getCsvFuncionarios() throws Exception {
        List<FuncionarioCsvDto> funcionarios = funcionarioService.getCsvFuncionarios();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
        StatefulBeanToCsv<FuncionarioCsvDto> sbc = new StatefulBeanToCsvBuilder<FuncionarioCsvDto>(writer)
                .withSeparator(',')
                .build();

        sbc.write(funcionarios);
        writer.flush();
        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=funcionarios.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

    @GetMapping("/empresa")
    public ResponseEntity<List<FuncionarioConsultaDto>> buscarPorEmpresa(@Param("idEmpresa") Integer idEmpresa) {
        List<FuncionarioConsultaDto> funcionarios = funcionarioService.buscarTodosPorEmpresa(idEmpresa);
        return ResponseEntity.status(200).body(funcionarios);
    }

    @PatchMapping("/status/{id}")
        public ResponseEntity<FuncionarioConsultaDto> alterarStatus(@PathVariable Integer id, @RequestBody FuncionarioAtualizacaoStatusDto funcionarioStatusDto) {
        FuncionarioConsultaDto funcionario = funcionarioService.alterarBitStatus(id, funcionarioStatusDto);
        return ResponseEntity.status(200).body(funcionario);
    }

}