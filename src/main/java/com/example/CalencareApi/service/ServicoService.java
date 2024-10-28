package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.servico.ServicoConsultaDto;
import com.example.CalencareApi.dto.servico.ServicoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaServico;
import com.example.CalencareApi.entity.Servico;
import com.example.CalencareApi.mapper.ServicoMapper;
import com.example.CalencareApi.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private CategoriaServicoService categoriaServicoService;

    public Servico cadastrar(ServicoCriacaoDto novoServico, Integer categoriaId) {
        if (novoServico == null) {
            return null;
        }

        CategoriaServico categoria = this.categoriaServicoService.buscarPorEntity(categoriaId);
        if (categoria == null) {
            return null;
        }

        Servico servico = ServicoMapper.toEntity(novoServico);
        servico.setCategoria(categoria);
        Servico servicoCadastrado = this.servicoRepository.save(servico);
        return servicoCadastrado;
    }

    /*public ServicoConsultaDto cadastrar(ServicoCriacaoDto novoServico, Integer categoriaId) {
        if (novoServico == null) {
            return null;
        }

        CategoriaServico categoria = this.categoriaServicoService.buscarPorEntity(categoriaId);
        if (categoria == null) {
            return null;
        }

        Servico servico = ServicoMapper.toEntity(novoServico);
        servico.setCategoria(categoria);
        Servico servicoCadastrado = this.servicoRepository.save(servico);
        ServicoConsultaDto dto = ServicoMapper.toDto(servicoCadastrado);
        return dto;
    }*/

    public ServicoConsultaDto buscarPorId(Integer id) {
        Servico servicoBusca = this.servicoRepository.findById(id).orElse(null);
        if (servicoBusca == null) {
            return null;
        }
        ServicoConsultaDto dto = ServicoMapper.toDto(servicoBusca);
        return dto;
    }

    public Servico buscarPorEntity(Integer id) {
        return this.servicoRepository.findById(id).orElse(null);
    }

    public Servico buscarPorNome(String nome) {
        return this.servicoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<ServicoConsultaDto> buscarTodos() {
        return ServicoMapper.toDto(this.servicoRepository.findAll());
    }

    public ServicoConsultaDto alterar(Integer id, ServicoCriacaoDto servico) {
        Servico servicoBusca = this.servicoRepository.findById(id).orElse(null);
        if (servicoBusca == null) {
            return null;
        }
        String nome = (servico.getNome() == null || servico.getNome().isEmpty()) ? servicoBusca.getNome() : servico.getNome();
        servicoBusca.setNome(nome);
        Servico servicoAlterado = this.servicoRepository.save(servicoBusca);
        ServicoConsultaDto dto = ServicoMapper.toDto(servicoAlterado);
        return dto;
    }

    public Boolean deletar(Integer id) {
        if (this.servicoRepository.findById(id).isEmpty()) {
            return false;
        }
        servicoRepository.deleteById(id);
        return true;
    }
}
