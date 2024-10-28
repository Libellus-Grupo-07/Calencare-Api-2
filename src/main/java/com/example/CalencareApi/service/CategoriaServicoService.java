package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoAtualizarDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoConsultaDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaServico;
import com.example.CalencareApi.mapper.CategoriaServicoMapper;
import com.example.CalencareApi.repository.CategoriaServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicoService { //implements CrudBasico

    @Autowired
    private CategoriaServicoRepository categoriaServicoRepository;

    public CategoriaServicoConsultaDto cadastrar(CategoriaServicoCriacaoDto novaCategoria) {
        if (novaCategoria == null) {
            return null;
        }
        CategoriaServico categoria = CategoriaServicoMapper.toEntity(novaCategoria);
        CategoriaServico categoriaCadastrada = this.categoriaServicoRepository.save(categoria);
        CategoriaServicoConsultaDto dto = CategoriaServicoMapper.toDto(categoriaCadastrada);
        return dto;
    }

    public List<CategoriaServicoConsultaDto> buscarTodos() {
        return CategoriaServicoMapper.toDto(this.categoriaServicoRepository.findAll());
    }

    public CategoriaServicoConsultaDto buscarPorId(Integer id) {
        Optional<CategoriaServico> categoriaBusca = this.categoriaServicoRepository.findById(id);

        if (categoriaBusca.isEmpty()) {
            return null;
        }

        CategoriaServicoConsultaDto dto = CategoriaServicoMapper.toDto(categoriaBusca.get());
        return dto;
    }

    public CategoriaServico buscarPorEntity(Integer id) {
        Optional<CategoriaServico> categoriaBusca = this.categoriaServicoRepository.findById(id);
        return categoriaBusca.orElse(null);
    }

    public CategoriaServicoConsultaDto alterar(Integer id, CategoriaServicoAtualizarDto categoria) {
        Optional<CategoriaServico> categoriaBusca = this.categoriaServicoRepository.findById(id);

        if (categoriaBusca.isEmpty()) {
            return null;
        }

        String nome = (categoria.getNome() == null || categoria.getNome().isEmpty()) ?
                categoriaBusca.get().getNome() : categoria.getNome();
        String descricao = (categoria.getDescricao() == null || categoria.getDescricao().isEmpty()) ?
                categoriaBusca.get().getDescricao() : categoria.getDescricao();

        CategoriaServico categoriaAlterada = categoriaBusca.get();
        categoriaAlterada.setNome(nome);
        categoriaAlterada.setDescricao(descricao);

        CategoriaServico categoriaAlteradaSalva = this.categoriaServicoRepository.save(categoriaAlterada);
        CategoriaServicoConsultaDto dto = CategoriaServicoMapper.toDto(categoriaAlteradaSalva);
        return dto;
    }

    public void deletar(Integer id) {
        try {
            this.categoriaServicoRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Erro ao deletar categoria de serviço: " + e.getMessage());
        }
    }
}
