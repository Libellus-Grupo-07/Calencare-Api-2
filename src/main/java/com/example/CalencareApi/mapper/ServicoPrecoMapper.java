package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.servicoPreco.ServicoPrecoConsultaDto;
import com.example.CalencareApi.dto.servicoPreco.ServicoPrecoCriacaoDto;
import com.example.CalencareApi.entity.ServicoPreco;

import java.util.List;

public class ServicoPrecoMapper {
    public static ServicoPreco toEntity(ServicoPrecoCriacaoDto servicoDto) {
        ServicoPreco servicoEntity = new ServicoPreco();
        servicoEntity.setDescricao(servicoDto.getDescricao());
        servicoEntity.setPreco(servicoDto.getPreco());
        servicoEntity.setDuracao(servicoDto.getDuracao());
        servicoEntity.setComissao(servicoDto.getComissao());
        servicoEntity.setBitStatus(servicoDto.getBitStatus());
        servicoEntity.setCategoria(servicoDto.getCategoria());
        return servicoEntity;
    }

    public static ServicoPrecoConsultaDto toDto(ServicoPreco servicoEntity) {
        ServicoPrecoConsultaDto servicoDto = new ServicoPrecoConsultaDto();
        servicoDto.setId(servicoEntity.getId());
        servicoDto.setNome(servicoEntity.getServico().getNome());
        servicoDto.setDescricao(servicoEntity.getDescricao());
        servicoDto.setPreco(servicoEntity.getPreco());
        servicoDto.setDuracao(servicoEntity.getDuracao());
        servicoDto.setComissao(servicoEntity.getComissao());
        servicoDto.setBitStatus(servicoEntity.getBitStatus());
        servicoDto.setServicoId(servicoEntity.getServico().getId());
        servicoDto.setCategoria(servicoEntity.getCategoria());
        return servicoDto;
    }

    public static List<ServicoPrecoConsultaDto> toDto(List<ServicoPreco> servicos) {
        return servicos.stream().map(ServicoPrecoMapper::toDto).toList();
    }
}
