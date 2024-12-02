package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.servicoPreco.ServicoPrecoAtualizacaoDto;
import com.example.CalencareApi.dto.servicoPreco.ServicoPrecoAtualizacaoPrecoDto;
import com.example.CalencareApi.dto.servicoPreco.ServicoPrecoConsultaDto;
import com.example.CalencareApi.dto.servicoPreco.ServicoPrecoCriacaoDto;
import com.example.CalencareApi.service.ServicoPrecoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/servico-preco")
public class ServicoPrecoController {
    private final ServicoPrecoService service;

    public ServicoPrecoController(ServicoPrecoService service) {
        this.service = service;
    }

    @GetMapping("/nao-usar/{id}")
    public ResponseEntity<ServicoPrecoConsultaDto> buscarServicoPrecoPorId(@PathVariable Integer id){
      if (!this.service.existePorId(id)){
          return ResponseEntity.status(404).build();
      }
      ServicoPrecoConsultaDto servioDto = this.service.buscarPorId(id);
      return ResponseEntity.status(200).body(servioDto);
    }

    @GetMapping("/nao-usar")
    public ResponseEntity<List<ServicoPrecoConsultaDto>> listarServicoPreco(){
        List<ServicoPrecoConsultaDto> servicoDto = this.service.listarTodos();

        if (servicoDto.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(servicoDto);
    }

    /*@PutMapping ("/nao-usar/{id}/{idCategoria}")
    public ResponseEntity<ServicoPrecoConsultaDto> atualizar(
            @PathVariable Integer id,
            @PathVariable Integer idCategoria,
            @Valid @RequestBody ServicoPrecoAtualizacaoDto servicoDto) {
      if(Objects.isNull(servicoDto)){
          return ResponseEntity.status(400).build();
      }

      if (!this.service.existePorId(id)){
          return ResponseEntity.status(404).build();
      }

      ServicoPrecoConsultaDto servicoPrecoAtualizadoDto = this.service.atualizar(id,servicoDto,idCategoria);
      return ResponseEntity.status(200).body(servicoPrecoAtualizadoDto);
    }*/

    @PatchMapping("/nao-usar/{id}/preco")
    public ResponseEntity<ServicoPrecoConsultaDto> atualizarPrecoServico(
            @PathVariable Integer id,
            @Valid @RequestBody ServicoPrecoAtualizacaoPrecoDto servicoDto){

        if (!this.service.existePorId(id)){
            return ResponseEntity.status(404).build();
        }

        ServicoPrecoConsultaDto precoServicoAtualizadoDto = this.service.atualizaPrecoServico(id, servicoDto);
        return ResponseEntity.status(200).body(precoServicoAtualizadoDto);
}

    @DeleteMapping("/nao-usar/{id}")
    public ResponseEntity<Void> excluirServicoPreco(@PathVariable Integer id){
        if (!this.service.existePorId(id)){
            return ResponseEntity.status(404).build();
        }

        this.service.excluirPorId(id);
        return ResponseEntity.status(204).build();
    }

    // Implementação Parâmetro por Empresa

    @PostMapping("/{idEmpresa}/{idCategoria}")
    public ResponseEntity<ServicoPrecoConsultaDto> cadastrar(
            @Valid @RequestBody ServicoPrecoCriacaoDto servicoPrecoDto,
            @PathVariable Integer idEmpresa,
            @PathVariable Integer idCategoria){

        if (Objects.isNull(servicoPrecoDto)){
            return ResponseEntity.status(400).build();
        }

        ServicoPrecoConsultaDto servicoPrecoCadastrado = this.service.cadastrar(servicoPrecoDto, idCategoria, idEmpresa);
        return ResponseEntity.status(200).body(servicoPrecoCadastrado);
    }

    @GetMapping("/{idEmpresa}/{id}")
    public ResponseEntity<ServicoPrecoConsultaDto> buscarServicoPrecoPorId(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer id){
        if (!this.service.existePorId(id)){
            return ResponseEntity.status(404).build();
        }
        ServicoPrecoConsultaDto dto = this.service.buscarPorId(idEmpresa,id);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<ServicoPrecoConsultaDto>> listarServicoPreco(@PathVariable Integer idEmpresa){
        List<ServicoPrecoConsultaDto> servicoDto = this.service.listarTodos(idEmpresa);
        if (servicoDto.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(servicoDto);
    }

    @PutMapping ("/{idEmpresa}/{id}/{idCategoria}")
    public ResponseEntity<ServicoPrecoConsultaDto> atualizar(
            @PathVariable Integer idEmpresa,
            @PathVariable Integer id,
            @PathVariable Integer idCategoria,
            @Valid @RequestBody ServicoPrecoAtualizacaoDto servicoDto) {
        if(Objects.isNull(servicoDto)){
            return ResponseEntity.status(400).build();
        }

        if (!this.service.existePorId(id)){
            return ResponseEntity.status(404).build();
        }

        ServicoPrecoConsultaDto servicoPrecoAtualizadoDto = this.service.atualizar(id,idEmpresa,servicoDto,idCategoria);
        return ResponseEntity.status(200).body(servicoPrecoAtualizadoDto);
    }

    @DeleteMapping("/{idEmpresa}/{id}")
    public ResponseEntity<Void> excluirServicoPreco(@PathVariable Integer idEmpresa, @PathVariable Integer id){
        Boolean servicoRemovido = this.service.excluirPorId(idEmpresa, id);
        if (!servicoRemovido){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }



}
