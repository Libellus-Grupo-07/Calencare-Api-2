package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.cliente.ClienteAtualizacaoDto;
import com.example.CalencareApi.dto.cliente.ClienteConsultaDto;
import com.example.CalencareApi.dto.cliente.ClienteCriacaoDto;
import com.example.CalencareApi.entity.Cliente;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.mapper.ClienteMapper;
import com.example.CalencareApi.repository.AgendamentoRepository;
import com.example.CalencareApi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    @Autowired private ClienteRepository repository;
    @Autowired private AgendamentoRepository agendamentoRepository;
    @Autowired private EmpresaService empresaService;

    public List<ClienteConsultaDto> listar(Integer idEmpresa){
        List<ClienteConsultaDto> clientes = ClienteMapper.toDto(repository.findByEmpresaId(idEmpresa));
        return clientes;
    }

    public ClienteConsultaDto buscarPorId(Integer id, Integer idEmpresa){
        Cliente cliente = repository.findByEmpresaIdAndId(idEmpresa, id);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return ClienteMapper.toDto(cliente);
    }

    public Cliente buscarEntidadePorId(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    public ClienteConsultaDto alterar(Integer id, ClienteAtualizacaoDto clienteDto, Integer idEmpresa){
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        if (empresa == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }
        if (clienteDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não informado");
        }

        String nome = clienteDto.getNome() != null ? clienteDto.getNome() : cliente.getNome();
        String sobrenome = clienteDto.getSobrenome() != null ? clienteDto.getSobrenome() : cliente.getSobrenome();
        String telefone = clienteDto.getTelefone() != null ? clienteDto.getTelefone() : cliente.getTelefone();
        String email = clienteDto.getEmail() != null ? clienteDto.getEmail() : cliente.getEmail();
        cliente.setNome(nome);
        cliente.setSobrenome(sobrenome);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);

        return ClienteMapper.toDto(repository.save(cliente));
    }

    public ClienteConsultaDto criar(ClienteCriacaoDto clienteDto){
        Empresa empresa = empresaService.buscarEntidadePorId(clienteDto.getEmpresaId());
        if (empresa == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada");
        }
        Cliente dto = ClienteMapper.toEntity(clienteDto);
        dto.setEmpresa(empresa);
        return ClienteMapper.toDto(repository.save(dto));
    }

    public void deletar(Integer id, Integer idEmpresa){
        Cliente cliente = repository.findByEmpresaIdAndId(idEmpresa, id);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        cliente.setBitStatus(4);
        repository.save(cliente);
    }

    public List<ClienteConsultaDto> buscarClienteNoAgendamentoPorEmpresaId(Integer id){
        if (agendamentoRepository.buscarClienteNoAgendamentoPorEmpresaId(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum cliente encontrado");
        }
        return ClienteMapper.toDto(agendamentoRepository.buscarClienteNoAgendamentoPorEmpresaId(id));
    }
}
