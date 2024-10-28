package com.example.CalencareApi.mapper;


import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeCriacaoDto;
import com.example.CalencareApi.entity.MovimentacaoValidade;

import java.time.LocalDateTime;
import java.util.List;

public class MovimentacaoValidadeMapper {

    public static MovimentacaoValidade toEntity(MovimentacaoValidadeCriacaoDto movimentacaoValidadeCriacaoDto) {
        MovimentacaoValidade movimentacaoValidade = new MovimentacaoValidade();
        movimentacaoValidade.setTipoMovimentacao(movimentacaoValidadeCriacaoDto.getTipoMovimentacao());
        movimentacaoValidade.setQuantidade(movimentacaoValidadeCriacaoDto.getQuantidade());
        movimentacaoValidade.setDtCriacao(LocalDateTime.now());
        movimentacaoValidade.setBitStatus(1);
        return movimentacaoValidade;
    }

    public static MovimentacaoValidadeConsultaDto toDto(MovimentacaoValidade movimentacaoValidade) {
        MovimentacaoValidadeConsultaDto movimentacaoValidadeConsultaDto = new MovimentacaoValidadeConsultaDto();
        movimentacaoValidadeConsultaDto.setId(movimentacaoValidade.getId());
        movimentacaoValidadeConsultaDto.setTipoMovimentacao(movimentacaoValidade.getTipoMovimentacao());
        movimentacaoValidadeConsultaDto.setQuantidade(movimentacaoValidade.getQuantidade());
        movimentacaoValidadeConsultaDto.setDtCriacao(movimentacaoValidade.getDtCriacao());
        movimentacaoValidadeConsultaDto.setIdValidade(movimentacaoValidade.getValidade().getId());
        return movimentacaoValidadeConsultaDto;
    }

    public static List<MovimentacaoValidadeConsultaDto> toDto(List<MovimentacaoValidade> movimentacaoValidades) {
        return movimentacaoValidades.stream().map(MovimentacaoValidadeMapper::toDto).toList();
    }
}
