package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.endereco.EnderecoAtualizacaoDto;
import com.example.CalencareApi.dto.endereco.EnderecoConsultaDto;
import com.example.CalencareApi.dto.endereco.EnderecoCriacaoDto;
import com.example.CalencareApi.entity.Endereco;

import java.util.List;

public class EnderecoMapper {
    public static Endereco toEntity(EnderecoCriacaoDto enderecoCriacaoDto) {
        Endereco endereco = new Endereco();

        endereco.setCep(enderecoCriacaoDto.getCep());
        endereco.setLogradouro(enderecoCriacaoDto.getLogradouro());
        endereco.setComplemento(enderecoCriacaoDto.getComplemento());
        endereco.setBairro(enderecoCriacaoDto.getBairro());
        endereco.setLocalidade(enderecoCriacaoDto.getLocalidade());
        endereco.setUf(enderecoCriacaoDto.getUf());
        endereco.setNumero(enderecoCriacaoDto.getNumero());
        endereco.setEmpresa(enderecoCriacaoDto.getEmpresa());

        return endereco;
    }

    public static Endereco toEntity(EnderecoAtualizacaoDto enderecoCriacaoDto) {
        Endereco endereco = new Endereco();

        endereco.setCep(enderecoCriacaoDto.getCep());
        endereco.setLogradouro(enderecoCriacaoDto.getLogradouro());
        endereco.setComplemento(enderecoCriacaoDto.getComplemento());
        endereco.setBairro(enderecoCriacaoDto.getBairro());
        endereco.setLocalidade(enderecoCriacaoDto.getLocalidade());
        endereco.setUf(enderecoCriacaoDto.getUf());
        endereco.setNumero(enderecoCriacaoDto.getNumero());
        endereco.setEmpresa(enderecoCriacaoDto.getEmpresa());

        return endereco;
    }

    public static EnderecoConsultaDto toDto(Endereco endereco) {
        EnderecoConsultaDto enderecoConsultaDto = new EnderecoConsultaDto();

        enderecoConsultaDto.setId(endereco.getId());
        enderecoConsultaDto.setCep(endereco.getCep());
        enderecoConsultaDto.setLogradouro(endereco.getLogradouro());
        enderecoConsultaDto.setComplemento(endereco.getComplemento());
        enderecoConsultaDto.setBairro(endereco.getBairro());
        enderecoConsultaDto.setLocalidade(endereco.getLocalidade());
        enderecoConsultaDto.setUf(endereco.getUf());
        enderecoConsultaDto.setNumero(endereco.getNumero());

        EnderecoConsultaDto.EmpresaConsultaDto empresa = new EnderecoConsultaDto.EmpresaConsultaDto();
        empresa.setId(endereco.getEmpresa().getId());
        empresa.setCnpj(endereco.getEmpresa().getCnpj());
        empresa.setRazaoSocial(endereco.getEmpresa().getRazaoSocial());
        empresa.setEmailPrincipal(endereco.getEmpresa().getEmailPrincipal());
        empresa.setTelefonePrincipal(endereco.getEmpresa().getTelefonePrincipal());
        enderecoConsultaDto.setEmpresa(empresa);

        return enderecoConsultaDto;
    }

    public static List<EnderecoConsultaDto> toDto(List<Endereco> enderecos) {
        return enderecos.stream().map(EnderecoMapper::toDto).toList();
    }
}
