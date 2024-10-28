package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.despesa.DespesaConsultaDto;
import com.example.CalencareApi.dto.despesa.DespesaCriacaoDto;
import com.example.CalencareApi.entity.Despesa;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class DespesaMapper {

    public static Despesa toEntity(DespesaCriacaoDto despesaDto) {
        Despesa despesaEntity = new Despesa();
        despesaEntity.setNome(despesaDto.getNome());
        despesaEntity.setObservacao(despesaDto.getObservacao());
        despesaEntity.setValor(despesaDto.getValor());
        despesaEntity.setFormaPagamento(despesaDto.getFormaPagamento());
        despesaEntity.setDtPagamento(despesaDto.getDtPagamento());
        despesaEntity.setDtCriacao(LocalDateTime.now());
        despesaEntity.setBitStatus(1);
        return despesaEntity;
    }

    public static DespesaConsultaDto toDto(Despesa despesaEntity) {
        DespesaConsultaDto despesaDto = new DespesaConsultaDto();
        despesaDto.setId(despesaEntity.getId());
        despesaDto.setNome(despesaEntity.getNome());
        despesaDto.setObservacao(despesaEntity.getObservacao());
        despesaDto.setValor(despesaEntity.getValor());
        despesaDto.setFormaPagamento(despesaEntity.getFormaPagamento());
        despesaDto.setDtPagamento(despesaEntity.getDtPagamento());
        despesaDto.setEmpresaId(despesaEntity.getEmpresa().getId());
        despesaDto.setCategoriaDespesaNome(despesaEntity.getCategoriaDespesa().getNome());
        despesaDto.setCategoriaDespesaId(despesaEntity.getCategoriaDespesa().getId());
        return despesaDto;
    }

    public static List<DespesaConsultaDto> toDto(List<Despesa> despesas) {
        return despesas.stream().map(DespesaMapper::toDto).toList();
    }
}