package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.funcionario.FuncionarioConsultaDto;
import com.example.CalencareApi.dto.servicoPorFuncionario.ServicoPorFuncionarioConsultaDto;
import com.example.CalencareApi.dto.servicoPorFuncionario.ServicoPorFuncionarioCriacaoDto;
import com.example.CalencareApi.service.ServicoPorFuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico-por-funcionario")
public class ServicoPorFuncionarioController {
    @Autowired private ServicoPorFuncionarioService service;

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<ServicoPorFuncionarioConsultaDto>> buscarTodosPorEmpresa(@PathVariable Integer idEmpresa){
        List<ServicoPorFuncionarioConsultaDto> servicos = this.service.buscarTodosPorEmpresa(idEmpresa);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{idEmpresa}/servico/{idServicoPreco}")
    public ResponseEntity<List<FuncionarioConsultaDto>> buscarServicoPorEmpresa(@PathVariable Integer idEmpresa,
                                                                     @PathVariable Integer idServicoPreco){
        List<FuncionarioConsultaDto> servicos = this.service.buscarServicoPorEmpresa(idEmpresa, idServicoPreco);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(200).body(servicos);
    }


    @GetMapping("/{idEmpresa}/funcionario/{idFuncionario}")
    public ResponseEntity<List<ServicoPorFuncionarioConsultaDto>> buscarServicoPorFuncionario(@PathVariable Integer idEmpresa,
                                                                             @PathVariable Integer idFuncionario){
        List<ServicoPorFuncionarioConsultaDto> servicos = this.service.buscarServicoPorFuncionario(idEmpresa, idFuncionario);
        if (servicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(this.service.buscarServicoPorFuncionario(idEmpresa,idFuncionario));
    }

    @GetMapping("/{idEmpresa}/{id}")
    public ResponseEntity<ServicoPorFuncionarioConsultaDto> buscarPorId(@PathVariable Integer idEmpresa,
                                                       @PathVariable Integer id){
        ServicoPorFuncionarioConsultaDto servico = this.service.buscarPorId(id, idEmpresa);
        if (servico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servico);
    }

    @PostMapping("/{idEmpresa}")
    public ResponseEntity<ServicoPorFuncionarioConsultaDto> cadastrar(
                                                    @PathVariable Integer idEmpresa,
                                                     @RequestBody @Valid ServicoPorFuncionarioCriacaoDto servico){

        ServicoPorFuncionarioConsultaDto dto = this.service.cadastrar(idEmpresa, servico);

        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{idEmpresa}/{idFuncionario}/{id}")
    public ResponseEntity<ServicoPorFuncionarioConsultaDto> alterarStatus(@PathVariable Integer idEmpresa,
                                                     @PathVariable Integer idFuncionario,
                                                     @PathVariable Integer id){
        return ResponseEntity.ok(this.service.alterarStatus(idEmpresa, idFuncionario, id));
    }

    @DeleteMapping("/{idEmpresa}/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @PathVariable Integer idEmpresa){
        this.service.deletar(id, idEmpresa);
        return ResponseEntity.ok().build();
    }
}
