package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.NotificacaoEstoqueConsultaDto;
import com.example.CalencareApi.dto.NotificacaoEstoqueCriacaoDto;
import com.example.CalencareApi.entity.NotificacaoEstoque;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NotificacaoEstoqueMapper {
    public static NotificacaoEstoque toEntity(NotificacaoEstoqueCriacaoDto notificacaoEstoqueCriacaoDto) {
        NotificacaoEstoque notificacaoEstoque = new NotificacaoEstoque();
        notificacaoEstoque.setNomeProduto(notificacaoEstoqueCriacaoDto.getNomeProduto());
        notificacaoEstoque.setNivelEstoque(notificacaoEstoqueCriacaoDto.getNivelEstoque());
        notificacaoEstoque.setQuantidade(notificacaoEstoqueCriacaoDto.getQuantidade());
        notificacaoEstoque.setLido(0);
        notificacaoEstoque.setExcluido(0);
        notificacaoEstoque.setDtCriacao(LocalDateTime.now());
        notificacaoEstoque.setDtExclusao(null);
        notificacaoEstoque.setDtLeitura(null);
        return notificacaoEstoque;
    }

    public static NotificacaoEstoqueConsultaDto toDto(NotificacaoEstoque notificacaoEstoque) {
        NotificacaoEstoqueConsultaDto notificacaoEstoqueConsultaDto = new NotificacaoEstoqueConsultaDto();
        notificacaoEstoqueConsultaDto.setId(notificacaoEstoque.getId());
        notificacaoEstoqueConsultaDto.setNomeProduto(notificacaoEstoque.getNomeProduto());
        notificacaoEstoqueConsultaDto.setNivelEstoque(notificacaoEstoque.getNivelEstoque());
        notificacaoEstoqueConsultaDto.setQuantidade(notificacaoEstoque.getQuantidade());
        notificacaoEstoqueConsultaDto.setLido(notificacaoEstoque.getLido());
        notificacaoEstoqueConsultaDto.setExcluido(notificacaoEstoque.getExcluido());
        notificacaoEstoqueConsultaDto.setDtCriacao(notificacaoEstoque.getDtCriacao());
        notificacaoEstoqueConsultaDto.setDtLeitura(notificacaoEstoque.getDtLeitura());
        notificacaoEstoqueConsultaDto.setDtExclusao(notificacaoEstoque.getDtExclusao());
        notificacaoEstoqueConsultaDto.setIdProduto(notificacaoEstoque.getProduto().getId());
        return notificacaoEstoqueConsultaDto;
    }

    public static List<NotificacaoEstoqueConsultaDto> toDto(List<NotificacaoEstoque> notificacoesEstoque) {
        return notificacoesEstoque.stream().map(NotificacaoEstoqueMapper::toDto).collect(Collectors.toList());
    }
}
