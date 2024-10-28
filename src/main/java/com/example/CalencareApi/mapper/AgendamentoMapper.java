package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.agendamento.AgendamentoConsultaDto;
import com.example.CalencareApi.dto.agendamento.AgendamentoCriacaoDto;
import com.example.CalencareApi.entity.Agendamento;

import java.util.List;
import java.util.Objects;

public class AgendamentoMapper {

    public static Agendamento toEntity(AgendamentoCriacaoDto dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setDtHora(dto.getDtHora());
        agendamento.setDia(dto.getDtHora().toLocalDate());
        agendamento.setHorario(dto.getDtHora().toLocalTime());
        agendamento.setBitStatus(1);
        //agendamento.setFuncionario(dto.getFuncionario());
        //agendamento.setCliente(dto.getCliente());
        //agendamento.setServicoPreco(dto.getServicoPreco());

        return agendamento;
    }

    public static AgendamentoConsultaDto toDto(Agendamento agendamentoEntity) {
        if (Objects.isNull(agendamentoEntity)) {
            return null;
        }

        AgendamentoConsultaDto dto = new AgendamentoConsultaDto();
        dto.setId(agendamentoEntity.getId());
        dto.setDtHora(agendamentoEntity.getDtHora());
        dto.setDia(agendamentoEntity.getDia());
        dto.setHorario(agendamentoEntity.getHorario());
        dto.setNomeFuncionario(agendamentoEntity.getFuncionario().getNome());
        dto.setNomeCliente(agendamentoEntity.getCliente().getNome());
        dto.setNomeServico(agendamentoEntity.getServicoPreco().getServico().getNome());
        dto.setStatus(agendamentoEntity.getBitStatus());
        dto.setPreco(agendamentoEntity.getServicoPreco().getPreco());
        dto.setComissao(agendamentoEntity.getServicoPreco().getComissao());
        dto.setDuracaoServico(agendamentoEntity.getServicoPreco().getDuracao());
        dto.setMetodoPagamento(agendamentoEntity.getMetodoPagamento());
        dto.setValorServico(agendamentoEntity.getServicoPreco().getPreco());
        dto.setTelefoneCliente(agendamentoEntity.getCliente().getTelefone());
        dto.setFuncionarioId(agendamentoEntity.getFuncionario().getId());
        dto.setClienteId(agendamentoEntity.getCliente().getId());
        dto.setServicoPrecoId(agendamentoEntity.getServicoPreco().getId());
        dto.setMetodoPagamento(agendamentoEntity.getMetodoPagamento());

        return dto;
    }

    public static List<AgendamentoConsultaDto> toDto(List<Agendamento> agendamentos) {
        return  agendamentos.stream()
                .map(AgendamentoMapper::toDto)
                .toList();
    }
}
