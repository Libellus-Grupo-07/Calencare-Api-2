package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaConsultaDto;
import com.example.CalencareApi.dto.categoria.despesa.CategoriaDespesaCriacaoDto;
import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.mapper.CategoriaDespesaMapper;
import com.example.CalencareApi.repository.CategoriaDespesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class CategoriaDespesaService {

    private final CategoriaDespesaRepository categoriaDespesaRepository;

    public CategoriaDespesaService(CategoriaDespesaRepository categoriaDespesaRepository) {
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }

    public CategoriaDespesaConsultaDto cadastrar(CategoriaDespesaCriacaoDto novaCategoriaDespesa) {
        if (Objects.isNull(novaCategoriaDespesa)) {
            return null;
        }
        CategoriaDespesa categoriaDespesa = CategoriaDespesaMapper.toEntity(novaCategoriaDespesa);
        CategoriaDespesa categoriaDespesaCadastrada = this.categoriaDespesaRepository.save(categoriaDespesa);
        CategoriaDespesaConsultaDto dto = CategoriaDespesaMapper.toDto(categoriaDespesaCadastrada);
        return dto;
    }


    public List<CategoriaDespesaConsultaDto> Listar() {
             return CategoriaDespesaMapper.toDto(this.categoriaDespesaRepository.findAll());
    }

    public CategoriaDespesaConsultaDto buscarPorId(Integer id) {
        Optional<CategoriaDespesa> categoriaDespesaBusca = this.categoriaDespesaRepository.findById(id);

        if (categoriaDespesaBusca.isEmpty()) {
            return null;
        }

        CategoriaDespesaConsultaDto dto = CategoriaDespesaMapper.toDto(categoriaDespesaBusca.get());

        return dto;
    }

    public CategoriaDespesa buscarEntidadePorId(Integer id) {
        return this.categoriaDespesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Boolean existeNome(String nome){ return this.categoriaDespesaRepository.existsCategoriaDespesaByNome(nome);}

    public Boolean existePorId(Integer id) {
        return this.categoriaDespesaRepository.existsById(id);
    }
}
