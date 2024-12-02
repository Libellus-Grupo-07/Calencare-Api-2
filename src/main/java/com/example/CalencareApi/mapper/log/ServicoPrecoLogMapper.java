package com.example.CalencareApi.mapper.log;
import com.example.CalencareApi.dto.log.ServicoPrecoLogConsultaDto;
import com.example.CalencareApi.dto.log.ServicoPrecoLogCriacaoDto;
import com.example.CalencareApi.entity.log.ServicoPrecoLog;
import java.time.LocalDateTime;

public class ServicoPrecoLogMapper {

    public static ServicoPrecoLog toEntity(ServicoPrecoLogCriacaoDto dto) {
        ServicoPrecoLog entity = new ServicoPrecoLog();
        entity.setDtCriacao(LocalDateTime.now());
        entity.setPrecoAtual(dto.getPrecoAtual());
        entity.setPrecoAnterior(dto.getPrecoAnterior());
        entity.setDuracaoAtual(dto.getDuracaoAtual());
        entity.setDuracaoAnterior(dto.getDuracaoAnterior());
        entity.setComissaoAtual(dto.getComissaoAtual());
        entity.setComissaoAnterior(dto.getComissaoAnterior());
        return entity;
    }

    public static ServicoPrecoLogConsultaDto toDto(ServicoPrecoLog entity) {
        ServicoPrecoLogConsultaDto dto = new ServicoPrecoLogConsultaDto();
        dto.setId(entity.getId());
        dto.setDtCriacao(entity.getDtCriacao().toString());
        dto.setPrecoAtual(entity.getPrecoAtual());
        dto.setPrecoAnterior(entity.getPrecoAnterior());
        dto.setDuracaoAtual(entity.getDuracaoAtual());
        dto.setDuracaoAnterior(entity.getDuracaoAnterior());
        dto.setComissaoAtual(entity.getComissaoAtual());
        dto.setComissaoAnterior(entity.getComissaoAnterior());
        dto.setServicoPrecoId(entity.getServicoPreco().getId());
        return dto;
    }
}
