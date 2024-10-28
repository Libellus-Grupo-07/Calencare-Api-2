package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaConsultaDto;
import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaCriacaoDto;
import com.example.CalencareApi.entity.CategoriaDespesa;

import java.util.List;

public class CategoriaDespesaMapper {

    public static CategoriaDespesa toEntity(CategoriaDespesaCriacaoDto categoriaDespesaDto) {
        CategoriaDespesa categoriaDespesaEntity = new CategoriaDespesa();
        categoriaDespesaEntity.setNome(categoriaDespesaDto.getNome());
        return categoriaDespesaEntity;
    }

    public static CategoriaDespesaConsultaDto toDto(CategoriaDespesa categoriaDespesaEntity) {
        CategoriaDespesaConsultaDto categoriaDespesaDto = new CategoriaDespesaConsultaDto();
        categoriaDespesaDto.setId(categoriaDespesaEntity.getId());
        categoriaDespesaDto.setNome(categoriaDespesaEntity.getNome());
        return categoriaDespesaDto;
    }
    public static List<CategoriaDespesaConsultaDto> toDto(List<CategoriaDespesa> categoriasDespesa) {
        return categoriasDespesa.stream().map(CategoriaDespesaMapper::toDto).toList();
    }
}
