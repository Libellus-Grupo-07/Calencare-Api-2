package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.empresa.EmpresaAtualizacaoDto;
import com.example.CalencareApi.dto.empresa.EmpresaConsultaDto;
import com.example.CalencareApi.dto.empresa.EmpresaCriacaoDto;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.Funcionario;
import com.example.CalencareApi.entity.HorarioFuncionamento;

import java.util.ArrayList;
import java.util.List;

public class EmpresaMapper {

    public static Empresa toEntity(EmpresaCriacaoDto empresaDto) {
        Empresa empresaEntity = new Empresa();
        empresaEntity.setCnpj(empresaDto.getCnpj());
        empresaEntity.setRazaoSocial(empresaDto.getRazaoSocial());
        empresaEntity.setEmailPrincipal(empresaDto.getEmailPrincipal());
        empresaEntity.setTelefonePrincipal(empresaDto.getTelefonePrincipal());
        empresaEntity.setIntervaloAtendimento(empresaDto.getIntervaloAtendimento());
        empresaEntity.setFuncionarios(new ArrayList<>());
        empresaEntity.setHorarioFuncionamentos(new ArrayList<>());
        return empresaEntity;
    }

    public static EmpresaConsultaDto toDto(Empresa empresaEntity) {
        if (empresaEntity == null) {
            return null;
        }

        EmpresaConsultaDto empresaDto = new EmpresaConsultaDto();
        empresaDto.setId(empresaEntity.getId());
        empresaDto.setCnpj(empresaEntity.getCnpj());
        empresaDto.setRazaoSocial(empresaEntity.getRazaoSocial());
        empresaDto.setTelefonePrincipal(empresaEntity.getTelefonePrincipal());
        empresaDto.setEmailPrincipal(empresaEntity.getEmailPrincipal());
        empresaDto.setDtCriacao(empresaEntity.getDtCriacao());
        empresaDto.setIntervaloAtendimento(empresaEntity.getIntervaloAtendimento());

        List<EmpresaConsultaDto.FuncionarioConsultaDto> funcionarios = new ArrayList<>();
        List<EmpresaConsultaDto.HorarioFuncionamentoConsultaDto> horariosFuncionamento = new ArrayList<>();

        if (empresaEntity.getFuncionarios() != null) {
            for (Funcionario funcionario : empresaEntity.getFuncionarios()) {
                EmpresaConsultaDto.FuncionarioConsultaDto funcionarioDto = new EmpresaConsultaDto.FuncionarioConsultaDto();
                funcionarioDto.setId(funcionario.getId());
                funcionarioDto.setNome(funcionario.getNome());
                funcionarioDto.setTelefone(funcionario.getTelefone());
                funcionarioDto.setEmail(funcionario.getEmail());
                funcionarioDto.setBitStatus(funcionario.getBitStatus());
                funcionarioDto.setDtCriacao(funcionario.getDtCriacao());
                funcionarios.add(funcionarioDto);
            }
        }

        if (empresaEntity.getHorarioFuncionamentos() != null) {
            for (HorarioFuncionamento funcionamento : empresaEntity.getHorarioFuncionamentos()) {
                EmpresaConsultaDto.HorarioFuncionamentoConsultaDto funcionamentoDto = new EmpresaConsultaDto.HorarioFuncionamentoConsultaDto();
                funcionamentoDto.setId(funcionamento.getId());
                funcionamentoDto.setDiaSemana(funcionamento.getDiaSemana());
                funcionamentoDto.setInicio(funcionamento.getInicio());
                funcionamentoDto.setFim(funcionamento.getFim());
                funcionamentoDto.setStatus(funcionamento.getStatus());
                horariosFuncionamento.add(funcionamentoDto);
            }
        }

        empresaDto.setFuncionarios(funcionarios);
        empresaDto.setHorariosFuncionamentos(horariosFuncionamento);

        return empresaDto;
    }

    public static Empresa toUpdateEntity(EmpresaAtualizacaoDto empresaDto){
        Empresa empresaEntity = new Empresa();
        empresaEntity.setRazaoSocial(empresaDto.getRazaoSocial());
        empresaEntity.setEmailPrincipal(empresaDto.getEmailPrincipal());
        empresaEntity.setTelefonePrincipal(empresaDto.getTelefonePrincipal());
        empresaEntity.setIntervaloAtendimento(empresaDto.getIntervaloAtendimento());
        return empresaEntity;
    }



    public static List<EmpresaConsultaDto> toDto(List<Empresa> empresas) {
        if (empresas == null) {
            return new ArrayList<>();
        }
        return empresas.stream().map(EmpresaMapper::toDto).toList();
    }
}
