package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoConsultaDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaServico;

import java.util.List;

public class CategoriaServicoMapper{

    public static CategoriaServico toEntity(CategoriaServicoCriacaoDto categoriaServicoDto) {
        CategoriaServico categoriaServicoEntity = new CategoriaServico();
        categoriaServicoEntity.setNome(categoriaServicoDto.getNome());
        categoriaServicoEntity.setDescricao(categoriaServicoDto.getDescricao());
        return categoriaServicoEntity;
    }

    public static CategoriaServicoConsultaDto toDto(CategoriaServico categoriaServicoEntity) {
        CategoriaServicoConsultaDto categoriaServicoDto = new CategoriaServicoConsultaDto();
        categoriaServicoDto.setId(categoriaServicoEntity.getId());
        categoriaServicoDto.setNome(categoriaServicoEntity.getNome());
        categoriaServicoDto.setDescricao(categoriaServicoEntity.getDescricao());
        return categoriaServicoDto;
    }

    public static List<CategoriaServicoConsultaDto> toDto(List<CategoriaServico> categoriaServicos) {
        return categoriaServicos.stream().map(CategoriaServicoMapper::toDto).toList();
    }

    /*public static CategoriaServico atualizarEntity(CategoriaServicoAtualizarDto categoriaServicoDto) {
        CategoriaServico categoriaServicoEntity = new CategoriaServico();




        categoriaServicoEntity.setNome(categoriaServicoDto.getNome());
        categoriaServicoEntity.setDescricao(categoriaServicoDto.getDescricao());
        return categoriaServicoEntity;
    }*/
}
