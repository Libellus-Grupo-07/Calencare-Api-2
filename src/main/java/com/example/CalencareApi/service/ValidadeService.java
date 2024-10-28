package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.validade.ValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.ValidadeCriacaoDto;
import com.example.CalencareApi.entity.Produto;
import com.example.CalencareApi.entity.Validade;
import com.example.CalencareApi.mapper.ValidadeMapper;
import com.example.CalencareApi.repository.MovimentacaoValidadeRepository;
import com.example.CalencareApi.repository.ProdutoRepository;
import com.example.CalencareApi.repository.ValidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidadeService {

    @Autowired ValidadeRepository validadeRepository;
    @Autowired
    MovimentacaoValidadeRepository movimentacaoValidadeRepository;
    @Autowired ProdutoRepository produtoRepository;

    public ValidadeConsultaDto cadastrar(ValidadeCriacaoDto validadeCriacaoDto) {
        Produto produto = produtoRepository.findById(validadeCriacaoDto.getIdProduto()).orElseThrow();
        Validade validade = ValidadeMapper.toEntity(validadeCriacaoDto);
        if (validade.getDtValidade() == null) {
            validade.setDescricao("Indefinido");
        }
        validade.setProduto(produto);
        validade = validadeRepository.save(validade);
        return ValidadeMapper.toDto(validade);
    }

    public ValidadeConsultaDto buscarPorId(Integer id) {
        Validade validade = validadeRepository.findById(id).orElse(null);
        if (validade == null) {
            return null;
        }
        return ValidadeMapper.toDto(validade);
    }

    public List<ValidadeConsultaDto> buscarPorIdProduto(Integer idProduto) {
        List<Validade> validades = validadeRepository.findByProdutoId(idProduto);
        return ValidadeMapper.toDto(validades);
    }

    public Boolean deletar(Integer id) {
        Validade validade = validadeRepository.findById(id).orElse(null);
        if (validade != null) {
            validade.setBitStatus(0);
            validadeRepository.save(validade);
            movimentacaoValidadeRepository.deleteByValidadeId(id);
            return true;
        }
        return false;
    }
}
