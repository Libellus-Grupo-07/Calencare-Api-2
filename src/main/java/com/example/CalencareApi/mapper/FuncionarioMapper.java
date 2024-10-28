package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.funcionario.*;
import com.example.CalencareApi.entity.Funcionario;

import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioMapper {
    public static Funcionario of(FuncionarioCriacaoDto funcionarioCriacaoDto) {
        Funcionario funcionario = new Funcionario();

        funcionario.setEmail(funcionarioCriacaoDto.getEmail());
        funcionario.setNome(funcionarioCriacaoDto.getNome());
        funcionario.setTelefone(funcionarioCriacaoDto.getTelefone());
        funcionario.setSenha(funcionarioCriacaoDto.getSenha());
        funcionario.setEmpresa(funcionarioCriacaoDto.getEmpresa());
        funcionario.setPerfilAcesso(funcionarioCriacaoDto.getPerfilAcesso());
        funcionario.setBitStatus(1);

        return funcionario;
    }

    public static FuncionarioTokenDto of(Funcionario funcionario, String token) {
        FuncionarioTokenDto funcionarioTokenDto = new FuncionarioTokenDto();

        funcionarioTokenDto.setUserId(funcionario.getId());
        funcionarioTokenDto.setEmail(funcionario.getEmail());
        funcionarioTokenDto.setNome(funcionario.getNome());
        funcionarioTokenDto.setToken(token);

        return funcionarioTokenDto;
    }

    public static FuncionarioConsultaDto toDto(Funcionario funcionario) {
        FuncionarioConsultaDto funcionarioConsultaDto = new FuncionarioConsultaDto();

        funcionarioConsultaDto.setId(funcionario.getId());
        funcionarioConsultaDto.setNome(funcionario.getNome());
        funcionarioConsultaDto.setTelefone(funcionario.getTelefone());
        funcionarioConsultaDto.setEmail(funcionario.getEmail());
        funcionarioConsultaDto.setBitStatus(funcionario.getBitStatus());
        funcionarioConsultaDto.setDtCriacao(funcionario.getDtCriacao());
        funcionarioConsultaDto.setPerfilAcesso(funcionario.getPerfilAcesso());

        FuncionarioConsultaDto.EmpresaConsulta empresa = new FuncionarioConsultaDto.EmpresaConsulta();
        empresa.setId(funcionario.getEmpresa().getId());
        empresa.setCnpj(funcionario.getEmpresa().getCnpj());
        empresa.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        empresa.setEmailPrincipal(funcionario.getEmpresa().getEmailPrincipal());
        empresa.setTelefonePrincipal(funcionario.getEmpresa().getTelefonePrincipal());
        funcionarioConsultaDto.setEmpresa(empresa);


        return funcionarioConsultaDto;
    }

    public static Funcionario toEntidade(FuncionarioAtualizacaoDto funcionarioAtualizacaoDto) {
        Funcionario funcionario = new Funcionario();

            funcionario.setEmail(funcionarioAtualizacaoDto.getEmail());
            funcionario.setNome(funcionarioAtualizacaoDto.getNome());
            funcionario.setTelefone(funcionarioAtualizacaoDto.getTelefone());
            funcionario.setPerfilAcesso(funcionarioAtualizacaoDto.getPerfilAcesso());
            funcionario.setBitStatus(funcionarioAtualizacaoDto.getBitStatus());
            return funcionario;
    }





    public static List<FuncionarioConsultaDto> toDto(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(FuncionarioMapper::toDto).collect(Collectors.toList());
    }

    public static FuncionarioCsvDto ofCsv(Funcionario funcionario) {
        FuncionarioCsvDto funcionarioCsvDto = new FuncionarioCsvDto();

        funcionarioCsvDto.setId(funcionario.getId());
        funcionarioCsvDto.setNome(funcionario.getNome());
        funcionarioCsvDto.setTelefone(funcionario.getTelefone());
        funcionarioCsvDto.setEmail(funcionario.getEmail());

        return funcionarioCsvDto;
    }

    public static List<FuncionarioCsvDto> ofCsv(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(FuncionarioMapper::ofCsv).collect(Collectors.toList());
    }


}
