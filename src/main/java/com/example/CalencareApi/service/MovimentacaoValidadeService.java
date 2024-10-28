package com.example.CalencareApi.service;
import com.example.CalencareApi.dto.produto.ProdutoConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeCriacaoDto;
import com.example.CalencareApi.entity.MovimentacaoValidade;
import com.example.CalencareApi.entity.Validade;
import com.example.CalencareApi.mapper.MovimentacaoValidadeMapper;
import com.example.CalencareApi.repository.MovimentacaoValidadeRepository;
import com.example.CalencareApi.repository.ValidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MovimentacaoValidadeService {

    @Autowired MovimentacaoValidadeRepository movimentacaoValidadeRepository;
    @Autowired ValidadeRepository validadeRepository;
    @Autowired ProdutoService produtoService;

    public MovimentacaoValidadeConsultaDto cadastrar(MovimentacaoValidadeCriacaoDto dto) {
        Validade validade = validadeRepository.findById(dto.getIdValidade()).orElseThrow();
        MovimentacaoValidade movimentacaoValidade = MovimentacaoValidadeMapper.toEntity(dto);
        movimentacaoValidade.setValidade(validade);
        Integer quantidade = retornarQuantidadePorValidade(dto.getIdValidade());

        if (quantidade == 0 && dto.getTipoMovimentacao() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível retirar um produto sem estoque");
        }

        if (quantidade + dto.getQuantidade() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque negativo inválido");
        }

        if (quantidade < dto.getQuantidade() && dto.getTipoMovimentacao() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível retirar mais do que o estoque");
        }

        if (movimentacaoValidade.getTipoMovimentacao() == 0) {
            movimentacaoValidade.setQuantidade(movimentacaoValidade.getQuantidade() * -1);
        }

        movimentacaoValidade = movimentacaoValidadeRepository.save(movimentacaoValidade);
        return MovimentacaoValidadeMapper.toDto(movimentacaoValidade);
    }

    public Integer retornarQuantidadePorValidade(Integer idValidade) {
        List<MovimentacaoValidade> movimentacaoValidade = movimentacaoValidadeRepository.findByValidadeId(idValidade);
        if (movimentacaoValidade == null) {
            return null;
        }
        Integer quantidade = 0;
        for (MovimentacaoValidade mv : movimentacaoValidade) {
            quantidade += mv.getQuantidade();
        }
        return quantidade;
    }

    public Integer retornarQuantidadeTodasValidadesProduto (Integer idProduto) {
        List<Validade> validades = validadeRepository.findByProdutoId(idProduto);
        if (validades == null) {
            return null;
        }
        Integer quantidade = 0;
        for (Validade validade : validades) {
            quantidade += retornarQuantidadePorValidade(validade.getId());
        }
        return quantidade;
    }

    public MovimentacaoValidadeConsultaDto buscarPorId(Integer id) {
        MovimentacaoValidade movimentacaoValidade = movimentacaoValidadeRepository.findByMovId(id);
        if (movimentacaoValidade == null) {
            return null;
        }
        return MovimentacaoValidadeMapper.toDto(movimentacaoValidade);
    }

    public List<MovimentacaoValidadeConsultaDto> buscarPorValidadeId (Integer idValidade) {
        Validade validade = validadeRepository.findById(idValidade).orElse(null);
        if (validade == null) {
            return null;
        }
        List<MovimentacaoValidade> movimentacaoValidade = movimentacaoValidadeRepository.findByValidadeId(idValidade);
        return MovimentacaoValidadeMapper.toDto(movimentacaoValidade);
    }

    // KPIS
    /*public Double retornarMediaEstoqueProduto(Integer idProduto) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idProduto);
    }*/

    public List<Map<String,Object>> retornarQuantidadeMediaProdutos(Integer idEmpresa) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idEmpresa);
    }

    public Integer retornarQuantidadeProdutosAlta (Integer idEmpresa) {
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTodasValidadesProduto(idProduto);
            Double porcentagemRelativa = (media * 100) / qntdAtual;
            if (porcentagemRelativa > 30.0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosBaixa (Integer idEmpresa) {
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTodasValidadesProduto(idProduto);
            Double porcentagemRelativa = (media * 100) / qntdAtual;
            if (porcentagemRelativa <= 30.0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosSemEstoque (Integer idEmpresa) {
        List<ProdutoConsultaDto> produtos =  produtoService.listarPorEmpresaId(idEmpresa);
        Integer contador = 0;

        for (ProdutoConsultaDto produto : produtos) {
            Integer qntdAtual = retornarQuantidadeTodasValidadesProduto(produto.getId());
            if (qntdAtual == 0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosRepostosDia(Integer idEmpresa, LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = dataInicio.plusHours(23).plusMinutes(59).plusSeconds(59);
        Integer contador = movimentacaoValidadeRepository.findReposicaoByData(idEmpresa, dataInicio, dataFim);
        return contador;
    }

    /*
     * ESTOQUE ALTO OK
     * SEM ESTOQUE OK
     * RESPOSTOS NO DIA OK
     * ESTOQUE BAIXO -> CONSIDERAR MÉDIA DE VALORES, CERCA DE 25% DESSA MÉDIA OK
     * ALERTA PARA VALIDADES PROXIMAS
     * AUTOMATICAMENTE DESATIVAR DATAS VENCIDAS
     * */
}
