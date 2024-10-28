package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.categoria.produto.CategoriaProdutoConsultaDto;
import com.example.CalencareApi.dto.categoria.produto.CategoriaProdutoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaProduto;

import java.util.List;

public class CategoriaProdutoMapper {
    public static CategoriaProduto toEntity(CategoriaProdutoCriacaoDto dto) {
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setNome(dto.getNome());
        return categoriaProduto;
    }

    public static CategoriaProdutoConsultaDto toDto(CategoriaProduto entity) {
        CategoriaProdutoConsultaDto categoriaProdutoDto = new CategoriaProdutoConsultaDto();
        categoriaProdutoDto.setId(entity.getId());
        categoriaProdutoDto.setNome(entity.getNome());
        return categoriaProdutoDto;
    }
    public static List<CategoriaProdutoConsultaDto> toDto(List<CategoriaProduto> entity) {
        return entity.stream().map(CategoriaProdutoMapper::toDto).toList();
    }
}
