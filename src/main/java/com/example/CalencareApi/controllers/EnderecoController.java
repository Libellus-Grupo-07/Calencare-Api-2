package com.example.CalencareApi.controllers;

import com.example.CalencareApi.api.via.cep.ViaCepService;
import com.example.CalencareApi.dto.endereco.EnderecoAtualizacaoDto;
import com.example.CalencareApi.dto.endereco.EnderecoConsultaDto;
import com.example.CalencareApi.dto.endereco.EnderecoCriacaoDto;
import com.example.CalencareApi.entity.Endereco;
import com.example.CalencareApi.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    @Autowired
    private EnderecoService service;
    @Autowired
    private ViaCepService viaCepService;
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/{empresa}")
    public ResponseEntity<EnderecoConsultaDto> criarEndereco(@PathVariable Integer empresa, @Valid @RequestBody EnderecoCriacaoDto enderecoCriacaoDto) {
        EnderecoConsultaDto enderecoCriado = service.criarEndereco(enderecoCriacaoDto.getCep(), empresa, enderecoCriacaoDto.getNumero(), enderecoCriacaoDto.getComplemento());
        return ResponseEntity.ok(enderecoCriado);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoConsultaDto>> listarEnderecos() {
        List<EnderecoConsultaDto> enderecos = service.listarEnderecos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoConsultaDto> buscarEnderecoPorId(@PathVariable Integer id) {
        EnderecoConsultaDto endereco = service.buscarEnderecoPorId(id);
        if (endereco != null) {
            return ResponseEntity.ok(endereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<EnderecoConsultaDto> buscarEnderecoPorIdEmpresa(@PathVariable Integer idEmpresa) {
        EnderecoConsultaDto endereco = service.buscarEnderecoPorIdEmpresa(idEmpresa);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoConsultaDto> atualizarEndereco(@PathVariable Integer id, @Valid @RequestBody EnderecoAtualizacaoDto enderecoDto) {
        EnderecoConsultaDto enderecoAtualizado = service.atualizarEndereco(id, enderecoDto);
        if (enderecoAtualizado != null) {
            return ResponseEntity.ok(enderecoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Integer id) {
        service.deletarEndereco(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/address/{cep}")
    public ResponseEntity<Endereco> getAddressByCep(@PathVariable String cep) {
        Endereco endereco = viaCepService.getEndereco(cep);
        return ResponseEntity.status(201).body(endereco);
    }

    @GetMapping("/ultimos")
    public ResponseEntity<Endereco[]> getUltimosenderecos() {
        List<Endereco> enderecos = enderecoService.getUltimosAgendamentos();
        Endereco[] enderecoArray = enderecos.toArray(new Endereco[0]);
        return ResponseEntity.status(200).body(enderecoArray);
    }

    @GetMapping("/pesquisa/{cidade}")
    public ResponseEntity<Endereco> pesquisaEnderecoPorCidade(@PathVariable String cidade) {
        Endereco endereco = enderecoService.pesquisaBinariaPorCidade(cidade);
        if (endereco != null) {
            return ResponseEntity.ok(endereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}