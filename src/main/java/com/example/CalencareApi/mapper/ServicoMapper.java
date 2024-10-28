package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.servico.ServicoConsultaDto;
import com.example.CalencareApi.dto.servico.ServicoCriacaoDto;
import com.example.CalencareApi.entity.Servico;

import java.util.List;

public class ServicoMapper {
    public static Servico toEntity(ServicoCriacaoDto dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        return servico;
    }

    public static ServicoConsultaDto toDto(Servico servico) {
        ServicoConsultaDto dto = new ServicoConsultaDto();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setCategoria(servico.getCategoria().getNome());
        return dto;
    }

    public static List<ServicoConsultaDto> toDto(List<Servico> servicos) {
        return servicos.stream().map(ServicoMapper::toDto).toList();
    }
}
