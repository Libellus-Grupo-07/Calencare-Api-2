package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.cliente.ClienteAtualizacaoDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.CalencareApi.dto.cliente.ClienteConsultaDto;
import com.example.CalencareApi.dto.cliente.ClienteCriacaoDto;
import com.example.CalencareApi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired private ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteConsultaDto> cadastrarCliente(@RequestBody @Valid ClienteCriacaoDto cliente) {
        ClienteConsultaDto clienteCadastrado = this.service.criar(cliente);
        return ResponseEntity.status(201).body(clienteCadastrado);
    }

    @GetMapping("/listar/{idEmpresa}")
    public ResponseEntity<List<ClienteConsultaDto>> listarClientes(@PathVariable Integer idEmpresa) {
        List<ClienteConsultaDto> clientes = this.service.listar(idEmpresa);
        if(clientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientes);
    }

    @GetMapping("/buscar/{idEmpresa}/{id}")
    public ResponseEntity<ClienteConsultaDto> buscarClientePorId(@PathVariable Integer id, @PathVariable Integer idEmpresa) {
        ClienteConsultaDto cliente = this.service.buscarPorId(id, idEmpresa);
        return ResponseEntity.status(200).body(cliente);
    }

    @PutMapping("/{idEmpresa}/{id}")
    public ResponseEntity<ClienteConsultaDto> atualizarClientePorId(@PathVariable Integer id,
                                                                    @PathVariable Integer idEmpresa,
                                                                    @RequestBody @Valid ClienteAtualizacaoDto cliente) {
        ClienteConsultaDto clienteAtualizado = this.service.alterar(id, cliente, idEmpresa);
        return ResponseEntity.status(200).body(clienteAtualizado);
    }

    @DeleteMapping("/{idEmpresa}/{id}")
    public ResponseEntity<Void> deletarClientePorId(@PathVariable Integer id, @PathVariable Integer idEmpresa) {
        this.service.deletar(id, idEmpresa);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<ClienteConsultaDto>> buscarClientesPorEmpresa(@PathVariable Integer id) {
        List<ClienteConsultaDto> clientes = this.service.buscarClienteNoAgendamentoPorEmpresaId(id);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientes);

    }

}
