package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.produto.ProdutoAtualizarDto;
import com.example.CalencareApi.dto.produto.ProdutoConsultaDto;
import com.example.CalencareApi.dto.produto.ProdutoCriacaoDto;
import com.example.CalencareApi.entity.Produto;

import java.time.LocalDateTime;
import java.util.List;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoCriacaoDto dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setMarca(dto.getMarca());
        produto.setBitStatus(1);
        produto.setDtCriacao(LocalDateTime.now());
        return produto;
    }

    public static ProdutoConsultaDto toDto(Produto dto) {
        ProdutoConsultaDto produto = new ProdutoConsultaDto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setMarca(dto.getMarca());
        produto.setCategoriaProdutoNome(dto.getCategoriaProduto().getNome());
        produto.setCategoriaProdutoId(dto.getCategoriaProduto().getId());
        produto.setEmpresaId(dto.getEmpresa().getId());
        return produto;
    }

    public static List<ProdutoConsultaDto> toDto(List<Produto> produtos) {
        return produtos.stream().map(ProdutoMapper::toDto).toList();
    }
}
