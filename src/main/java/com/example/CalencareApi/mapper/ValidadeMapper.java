package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.validade.ValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.ValidadeCriacaoDto;
import com.example.CalencareApi.entity.Validade;

import java.time.LocalDateTime;
import java.util.List;

public class ValidadeMapper {

    public static Validade toEntity(ValidadeCriacaoDto validadeCriacaoDto) {
        Validade validade = new Validade();
        validade.setDescricao(validadeCriacaoDto.getDescricao());
        validade.setDtValidade(validadeCriacaoDto.getDtValidade());
        validade.setDtCriacao(LocalDateTime.now());
        validade.setBitStatus(1);
        return validade;
    }

    public static ValidadeConsultaDto toDto(Validade validade) {
        ValidadeConsultaDto validadeConsultaDto = new ValidadeConsultaDto();
        validadeConsultaDto.setId(validade.getId());
        validadeConsultaDto.setDescricao(validade.getDescricao());
        if (validade.getDtValidade() != null) {
            validadeConsultaDto.setDtValidade(validade.getDtValidade().toString());
        }
        validadeConsultaDto.setBitStatus(validade.getBitStatus());
        validadeConsultaDto.setProdutoId(validade.getProduto().getId());
        return validadeConsultaDto;
    }

    public static List<ValidadeConsultaDto> toDto(List<Validade> validades) {
        return validades.stream().map(ValidadeMapper::toDto).toList();
    }
}
