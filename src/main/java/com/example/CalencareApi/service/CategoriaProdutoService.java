package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.categoria.produto.CategoriaProdutoConsultaDto;
import com.example.CalencareApi.dto.categoria.produto.CategoriaProdutoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaProduto;
import com.example.CalencareApi.mapper.CategoriaProdutoMapper;
import com.example.CalencareApi.repository.CategoriaProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoriaProdutoService {

    private final CategoriaProdutoRepository repository;

    public CategoriaProdutoService(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    public CategoriaProdutoConsultaDto cadastrar(CategoriaProdutoCriacaoDto novaCategoriaProduto) {
        if (Objects.isNull(novaCategoriaProduto)) {
            return null;
        }
        CategoriaProduto categoriaProduto = CategoriaProdutoMapper.toEntity(novaCategoriaProduto);
        CategoriaProduto categoriaProdutoCadastrada = this.repository.save(categoriaProduto);
        CategoriaProdutoConsultaDto dto = CategoriaProdutoMapper.toDto(categoriaProdutoCadastrada);
        return dto;
    }

    public CategoriaProdutoConsultaDto buscarPorId(Integer id) {
        return this.repository.findById(id)
            .map(CategoriaProdutoMapper::toDto)
            .orElse(null);
    }

    public List<CategoriaProdutoConsultaDto> listar() {
        return CategoriaProdutoMapper.toDto(this.repository.findAll());
    }

    public CategoriaProduto buscarEntidadePorId(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    public Boolean existeNome(String nome) {
        return this.repository.existsCategoriaProdutoByNome(nome);
    }

    public Boolean existePorId(Integer id) {
        return this.repository.existsById(id);
    }
}
