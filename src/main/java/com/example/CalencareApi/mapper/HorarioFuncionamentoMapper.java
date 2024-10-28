package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.horarioFuncionamento.HorarioFuncionamentoConsultaDto;
import com.example.CalencareApi.dto.horarioFuncionamento.HorarioFuncionamentoCriacaoDto;
import com.example.CalencareApi.entity.HorarioFuncionamento;

import java.util.List;

public class HorarioFuncionamentoMapper {

    public static HorarioFuncionamento toEntity(HorarioFuncionamentoCriacaoDto horarioFuncionamentoDto){

        HorarioFuncionamento horarioFuncionamentoEntity = new HorarioFuncionamento();

        horarioFuncionamentoEntity.setDiaSemana(horarioFuncionamentoDto.getDiaSemana());
        horarioFuncionamentoEntity.setCodDiaSemana(horarioFuncionamentoDto.getCodDiaSemana());
        horarioFuncionamentoEntity.setInicio(horarioFuncionamentoDto.getInicio());
        horarioFuncionamentoEntity.setFim(horarioFuncionamentoDto.getFim());
        horarioFuncionamentoEntity.setStatus(horarioFuncionamentoDto.getStatus());
        //horarioFuncionamentoEntity.setEmpresa(horarioFuncionamentoDto.getEmpresa());

        return horarioFuncionamentoEntity;
    }

    public static HorarioFuncionamentoConsultaDto toDto(HorarioFuncionamento horarioFuncionamentoEntity){

        HorarioFuncionamentoConsultaDto horarioFuncionamentoConsultaDtoDto = new HorarioFuncionamentoConsultaDto();

        horarioFuncionamentoConsultaDtoDto.setId(horarioFuncionamentoEntity.getId());
        horarioFuncionamentoConsultaDtoDto.setDiaSemana(horarioFuncionamentoEntity.getDiaSemana());
        horarioFuncionamentoConsultaDtoDto.setCodDiaSemana(horarioFuncionamentoEntity.getCodDiaSemana());
        horarioFuncionamentoConsultaDtoDto.setInicio(horarioFuncionamentoEntity.getInicio());
        horarioFuncionamentoConsultaDtoDto.setFim(horarioFuncionamentoEntity.getFim());
        horarioFuncionamentoConsultaDtoDto.setStatus(horarioFuncionamentoEntity.getStatus());
        //horarioFuncionamentoConsultaDtoDto.set(horarioFuncionamentoEntity.getEmpresa());

        return horarioFuncionamentoConsultaDtoDto;
    }

    public static List<HorarioFuncionamentoConsultaDto> toDto(List<HorarioFuncionamento> horariosFuncionamento){

        return horariosFuncionamento.stream().map(HorarioFuncionamentoMapper::toDto).toList();
    }
}
