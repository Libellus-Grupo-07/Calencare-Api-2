package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.servicoPorFuncionario.ServicoPorFuncionarioConsultaDto;
import com.example.CalencareApi.entity.ServicoPorFuncionario;

import java.util.List;

public class ServicoPorFuncionarioMapper {
    /*public static ServicoPorFuncionario toEntity(ServicoPorFuncionarioCriacaoDto servicoDto) {
        ServicoPorFuncionario servicoEntity = new ServicoPorFuncionario();
        servicoEntity.setDtAdd(LocalDateTime.now());
        servicoEntity.setBitStatus(1);
        return servicoEntity;
    }*/

    public static ServicoPorFuncionarioConsultaDto toDto(ServicoPorFuncionario servicoEntity) {
        ServicoPorFuncionarioConsultaDto servicoDto = new ServicoPorFuncionarioConsultaDto();
        servicoDto.setId(servicoEntity.getId());
        servicoDto.setNomeFuncionario(servicoEntity.getFuncionario().getNome());
        servicoDto.setCategoria(servicoEntity.getServicoPreco().getServico().getCategoria().getNome());
        servicoDto.setNomeServico(servicoEntity.getServicoPreco().getServico().getNome());
        servicoDto.setDuracao(servicoEntity.getServicoPreco().getDuracao());
        servicoDto.setPreco(servicoEntity.getServicoPreco().getPreco());
        servicoDto.setBitStatus(servicoEntity.getBitStatus());
        servicoDto.setFuncionarioId(servicoEntity.getFuncionario().getId());
        servicoDto.setServicoPrecoId(servicoEntity.getServicoPreco().getId());

        ServicoPorFuncionarioConsultaDto.FuncionarioConsultaDto funcionarioDto = new ServicoPorFuncionarioConsultaDto.FuncionarioConsultaDto();
        funcionarioDto.setId(servicoEntity.getFuncionario().getId());
        funcionarioDto.setNome(servicoEntity.getFuncionario().getNome());
        funcionarioDto.setTelefone(servicoEntity.getFuncionario().getTelefone());
        funcionarioDto.setEmail(servicoEntity.getFuncionario().getEmail());
        funcionarioDto.setBitStatus(servicoEntity.getFuncionario().getBitStatus());
        funcionarioDto.setDtCriacao(servicoEntity.getFuncionario().getDtCriacao());
        funcionarioDto.setPerfilAcesso(servicoEntity.getFuncionario().getPerfilAcesso());
        servicoDto.setFuncionario(funcionarioDto);

        ServicoPorFuncionarioConsultaDto.ServicoPrecoConsultaDto servicoPrecoDto = new ServicoPorFuncionarioConsultaDto.ServicoPrecoConsultaDto();
        servicoPrecoDto.setId(servicoEntity.getServicoPreco().getId());
        servicoPrecoDto.setNome(servicoEntity.getServicoPreco().getServico().getNome());
        servicoPrecoDto.setCategoria(servicoEntity.getServicoPreco().getServico().getCategoria().getNome());
        servicoPrecoDto.setDuracao(servicoEntity.getServicoPreco().getDuracao());
        servicoPrecoDto.setPreco(servicoEntity.getServicoPreco().getPreco());
        servicoPrecoDto.setBitStatus(servicoEntity.getServicoPreco().getBitStatus());
        servicoPrecoDto.setDtCriacao(servicoEntity.getServicoPreco().getEmpresa().getDtCriacao());
        servicoDto.setServicoPreco(servicoPrecoDto);

        return servicoDto;
    }

    public static List<ServicoPorFuncionarioConsultaDto> toDto(List<ServicoPorFuncionario> servicos) {
        return servicos.stream().map(ServicoPorFuncionarioMapper::toDto).toList();
    }

}
