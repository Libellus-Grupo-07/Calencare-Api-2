package com.example.CalencareApi.service;
import com.example.CalencareApi.dto.empresa.EmpresaConsultaDto;
import com.example.CalencareApi.dto.funcionario.FuncionarioConsultaDto;
import com.example.CalencareApi.dto.produto.ProdutoConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeCriacaoDto;
import com.example.CalencareApi.entity.*;
import com.example.CalencareApi.mapper.MovimentacaoValidadeMapper;
import com.example.CalencareApi.mapper.ProdutoMapper;
import com.example.CalencareApi.repository.FuncionarioRepository;
import com.example.CalencareApi.repository.MovimentacaoValidadeRepository;
import com.example.CalencareApi.repository.ProdutoRepository;
import com.example.CalencareApi.repository.ValidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovimentacaoValidadeService {

    @Autowired MovimentacaoValidadeRepository movimentacaoValidadeRepository;
    @Autowired ValidadeRepository validadeRepository;
    @Autowired ProdutoRepository produtoRepository;
    @Autowired ProdutoService produtoService;
    @Autowired EmpresaService empresaService;
    @Autowired NotificacaoEstoqueService notificacaoEstoqueService;
    @Autowired FuncionarioService funcionarioService;
    @Autowired EmailService emailService;

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

    public Integer retornarQuantidadeTotalProduto(Integer idProduto) {
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

    // PRODUTO
    public ProdutoConsultaDto buscarPorIdPorEmpresa(Integer id, Integer idEmpresa) {
        Produto produto = this.produtoRepository.findByIdAndEmpresaId(id, idEmpresa);
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        if (produto == null) {
            return null;
        }
        if (!produto.getEmpresa().equals(empresa)) {
            return null;
        }
        ProdutoConsultaDto produtoConsultaDto = ProdutoMapper.toDto(produto);
        produtoConsultaDto.setQntdTotalEstoque(retornarQuantidadeTotalProduto(id));
        if (produtoConsultaDto.getQntdTotalEstoque() == 0) {
            produtoConsultaDto.setNivelEstoque("Sem estoque");
        }
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);

        for (int i = 0; i < mediaProdutos.size(); i++) {
            Map<String,Object> mediaProduto = mediaProdutos.get(i);
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;
            String nivelEstoque = "";
            if (idProduto == id) {
                if ( porcentagemRelativa > 15.0 && porcentagemRelativa <= 30.0 ) {
                    nivelEstoque = "Estoque baixo";
                } else if (porcentagemRelativa > 0.0 && porcentagemRelativa <= 15.0) {
                    nivelEstoque = "Quase sem estoque";
                } else if (qntdAtual == 0) {
                    nivelEstoque = "Sem estoque";
                } else {
                    nivelEstoque = "Estoque alto";
                }
                produtoConsultaDto.setNivelEstoque(nivelEstoque);
            }
        }
        return produtoConsultaDto;
    }

    public ProdutoConsultaDto buscarProdutoPorId(Integer id) {
        ProdutoConsultaDto produto = ProdutoMapper.toDto(produtoRepository.findById(id).orElse(null));
        if (produto == null) {
            return null;
        }
        Integer qntd = retornarQuantidadeTotalProduto(id);
        produto.setQntdTotalEstoque(qntd);
        return produto;
    }

    public List<ProdutoConsultaDto> buscarPorNomeOuMarca(Integer idEmpresa, String nome) {
        List<ProdutoConsultaDto> produtos = ProdutoMapper.toDto(this.produtoRepository.findByNomeOrMarca(idEmpresa, nome));
        List<ProdutoConsultaDto> produtosCopia = new ArrayList<>();
        List<ProdutoConsultaDto> produtosSemEstoque = new ArrayList<>();
        for (int i = 0; i < produtos.size(); i++) {
            produtosCopia.add(produtos.get(i));
        }

        for (int i = 0; i < produtosCopia.size(); i++) {
            ProdutoConsultaDto produto = produtosCopia.get(i);
            Integer qntd = retornarQuantidadeTotalProduto(produtosCopia.get(i).getId());

            if (qntd == 0) {
                produtosCopia.remove(i);
                produto.setNivelEstoque("Sem estoque");
                produtosSemEstoque.add(produto);
                i--;
            } else {
                produtosCopia.get(i).setQntdTotalEstoque(qntd);
            }
        }
        return produtosCopia;
    }


    public List<ProdutoConsultaDto> listarPorEmpresaId(Integer idEmpresa) {
        List<ProdutoConsultaDto> produtos = ProdutoMapper.toDto(this.produtoRepository.findAllByEmpresaId(idEmpresa));
        List<ProdutoConsultaDto> produtosCopia = new ArrayList<>();
        List<ProdutoConsultaDto> produtosSemEstoque = new ArrayList<>();
        for (int i = 0; i < produtos.size(); i++) {
            produtosCopia.add(produtos.get(i));
        }

        for (int i = 0; i < produtosCopia.size(); i++) {
            ProdutoConsultaDto produto = produtosCopia.get(i);
            Integer qntd = retornarQuantidadeTotalProduto(produtosCopia.get(i).getId());

            if (qntd == 0) {
                produtosCopia.remove(i);
                produto.setNivelEstoque("Sem estoque");
                produtosSemEstoque.add(produto);
                i--;
            } else {
                produtosCopia.get(i).setQntdTotalEstoque(qntd);
            }
        }

        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);

        for (int i = 0; i < mediaProdutos.size(); i++) {
            Map<String,Object> mediaProduto = mediaProdutos.get(i);
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;
            String nivelEstoque = "";
            if ( porcentagemRelativa > 15.0 && porcentagemRelativa <= 30.0 ) {
                nivelEstoque = "Estoque baixo";
            } else if (porcentagemRelativa > 0.0 && porcentagemRelativa <= 15.0) {
                nivelEstoque = "Quase sem estoque";
            } else if (qntdAtual == 0) {
                nivelEstoque = "Sem estoque";
            } else {
                nivelEstoque = "Estoque alto";
            }
            produtosCopia.get(i).setNivelEstoque(nivelEstoque);
        }

        produtosCopia.addAll(produtosSemEstoque);
        produtosCopia.sort((p1, p2) -> p1.getNome() == null ? -1 : p1.getNome().compareTo(p2.getNome()));

        return produtosCopia;
    }

    // KPIS
    /*public Double retornarMediaEstoqueProduto(Integer idProduto) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idProduto);
    }*/

    public List<Map<String,Object>> retornarQuantidadeMediaProdutos(Integer idEmpresa) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idEmpresa);
    }

    public List<Map<String,Object>> retornarQuantidadeMediaProdutos(Integer idEmpresa, Integer idProduto) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idEmpresa, idProduto);
    }

    public Integer retornarQuantidadeProdutosAlta (Integer idEmpresa) {
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;
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
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;
            if ( porcentagemRelativa > 15.0 && porcentagemRelativa <= 30.0 ) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosMuitoBaixa (Integer idEmpresa) {
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;
            if (porcentagemRelativa <= 15.0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosSemEstoque (Integer idEmpresa) {
        List<ProdutoConsultaDto> produtos =  listarPorEmpresaId(idEmpresa);
        Integer contador = 0;

        for (ProdutoConsultaDto produto : produtos) {
            Integer qntdAtual = retornarQuantidadeTotalProduto(produto.getId());
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


    // Listar produtos com estoque baixo
    public List<ProdutoConsultaDto> listarProdutosEstoqueBaixo(Integer idEmpresa) {
        List<ProdutoConsultaDto> produtosBaixo = new ArrayList<>();
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        if (mediaProdutos == null) {
            return null;
        }

        if (mediaProdutos.isEmpty()) {
            return null;
        }

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;

            if (porcentagemRelativa > 15.0 && porcentagemRelativa <= 30.0) {
                String descricao = "Estoque baixo";
                ProdutoConsultaDto produto = buscarProdutoPorId(idProduto);
                produto.setQntdTotalEstoque(qntdAtual);
                produto.setNivelEstoque(descricao);
                produtosBaixo.add(produto);
                notificacaoEstoqueService.cadastrar(produto, descricao);
            }
        }

        return produtosBaixo;
    }

    // Listar produtos com estoque muito baixo
    public List<ProdutoConsultaDto> listarProdutosEstoqueMuitoBaixo(Integer idEmpresa) {
        List<ProdutoConsultaDto> produtosMuitoBaixo = new ArrayList<>();
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        if (mediaProdutos == null) {
            return null;
        }

        if (mediaProdutos.isEmpty()) {
            return null;
        }

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (qntdAtual * 100) / media;

            if (porcentagemRelativa > 0.0 && porcentagemRelativa <= 15.0) {
                String descricao = "Quase sem estoque";
                ProdutoConsultaDto produto = buscarProdutoPorId(idProduto);
                produto.setNivelEstoque(descricao);
                produtosMuitoBaixo.add(produto);
                notificacaoEstoqueService.cadastrar(produto, descricao);
            }
        }
        return produtosMuitoBaixo;
    }

    // Listar produtos sem estoque
    public List<ProdutoConsultaDto> listarProdutosSemEstoque(Integer idEmpresa) {
        List<ProdutoConsultaDto> produtosSemEstoque = new ArrayList<>();
        List<ProdutoConsultaDto> produtos =  listarPorEmpresaId(idEmpresa);

        if (produtos == null) {
            return null;
        }

        if (produtos.isEmpty()) {
            return null;
        }

        for (ProdutoConsultaDto produto : produtos) {
            Integer qntdAtual = retornarQuantidadeTotalProduto(produto.getId());

            if (qntdAtual == 0) {
                produtosSemEstoque.add(produto);
                String descricao = "Sem estoque";
                notificacaoEstoqueService.cadastrar(produto, descricao);
            }
        }
        return produtosSemEstoque;
    }

    // Listar produtos com estoque em alerta
    public List<ProdutoConsultaDto> listarProdutosAlertaEstoque(Integer idEmpresa) {
        List<ProdutoConsultaDto> produtosAlerta = new ArrayList<>();
        produtosAlerta.addAll(listarProdutosEstoqueBaixo(idEmpresa));
        produtosAlerta.addAll(listarProdutosEstoqueMuitoBaixo(idEmpresa));
        produtosAlerta.addAll(listarProdutosSemEstoque(idEmpresa));
        produtosAlerta.sort((p1, p2) -> p1.getQntdTotalEstoque() - p2.getQntdTotalEstoque());
        return produtosAlerta;
    }

    // Listar todas as movimentações de um produto
    public List<MovimentacaoValidadeConsultaDto> listarMovimentacoesEmpresa(Integer idEmpresa) {
        List<Validade> validades = validadeRepository.findByEmpresaId(idEmpresa);
        List<MovimentacaoValidadeConsultaDto> movimentacoes = new ArrayList<>();
        for (Validade validade : validades) {
            List<MovimentacaoValidade> movimentacaoValidades = movimentacaoValidadeRepository.findByValidadeId(validade.getId());
            for (MovimentacaoValidade movimentacao : movimentacaoValidades) {
                movimentacoes.add(MovimentacaoValidadeMapper.toDto(movimentacao));
            }
        }
        return movimentacoes;
    }

    @Async
    @Scheduled(fixedDelay = 1000 * 180 * 1)
    @Transactional
    public void enviarEmailSituacaoEstoque() {
        List<EmpresaConsultaDto> empresas = empresaService.listarEmpresas();
        for (EmpresaConsultaDto empresa : empresas) {
            List<FuncionarioConsultaDto> admins = funcionarioService.listarAdminDto(empresa.getId());
            List<ProdutoConsultaDto> produtos = listarProdutosAlertaEstoque(empresa.getId());
            StringBuilder msgEstoque = new StringBuilder();
            for (FuncionarioConsultaDto admin : admins) {
                for (ProdutoConsultaDto produto : produtos) {
                    msgEstoque.append("Produto: %s /// Estoque: %d /// Nível de estoque: %s\n"
                            .formatted(produto.getNome(), produto.getQntdTotalEstoque(), produto.getNivelEstoque()));
                }
                String assunto = "Situação do estoque";
                String msg = """
                Olá, %s!
                Segue a situação do estoque da empresa %s
                
                %s"""
                        .formatted(admin.getNome(),empresa.getRazaoSocial(), msgEstoque);
                emailService.enviarEmailTexto(admin.getEmail(), "Situação de estoque", msg);
            }
        }
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
