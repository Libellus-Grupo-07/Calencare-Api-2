package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.cliente.ClienteConsultaDto;
import com.example.CalencareApi.dto.cliente.ClienteCriacaoDto;
import com.example.CalencareApi.entity.Cliente;

import java.util.List;

public class ClienteMapper {

    public static Cliente toEntity(ClienteCriacaoDto clienteCriacaoDto) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteCriacaoDto.getNome());
        cliente.setSobrenome(clienteCriacaoDto.getSobrenome());
        cliente.setTelefone(clienteCriacaoDto.getTelefone());
        cliente.setEmail(clienteCriacaoDto.getEmail());
        cliente.setBitStatus(1);
        return cliente;
    }

    public static ClienteConsultaDto toDto(Cliente cliente) {
        ClienteConsultaDto clienteConsultaDto = new ClienteConsultaDto();
        clienteConsultaDto.setId(cliente.getId());
        clienteConsultaDto.setNome(cliente.getNome());
        clienteConsultaDto.setSobrenome(cliente.getSobrenome());
        clienteConsultaDto.setTelefone(cliente.getTelefone());
        clienteConsultaDto.setEmail(cliente.getEmail());
        clienteConsultaDto.setDtCriacao(cliente.getDtCriacao());
        clienteConsultaDto.setEmpresaId(cliente.getEmpresa().getId());
        return clienteConsultaDto;
    }

    public static List<ClienteConsultaDto> toDto(List<Cliente> clientes) {
        return clientes.stream().map(ClienteMapper::toDto).toList();
    }
}
