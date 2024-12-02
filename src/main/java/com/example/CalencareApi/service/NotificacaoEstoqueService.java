package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.NotificacaoEstoqueConsultaDto;
import com.example.CalencareApi.dto.NotificacaoEstoqueCriacaoDto;
import com.example.CalencareApi.dto.produto.ProdutoConsultaDto;
import com.example.CalencareApi.entity.NotificacaoEstoque;
import com.example.CalencareApi.entity.Produto;
import com.example.CalencareApi.mapper.NotificacaoEstoqueMapper;
import com.example.CalencareApi.repository.NotificacaoEstoqueRepository;
import com.example.CalencareApi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificacaoEstoqueService {

    @Autowired private NotificacaoEstoqueRepository notificacaoEstoqueRepository;
    @Autowired private ProdutoRepository produtoRepository;

    public void cadastrar(ProdutoConsultaDto produtoConsultaDto, String nivelEstoque) {
        Produto produto = produtoRepository.findById(produtoConsultaDto.getId())
                .orElseThrow( () -> new RuntimeException("Produto não encontrado"));

        NotificacaoEstoque ultimaNotificacao = notificacaoEstoqueRepository.findByProdutoId(produtoConsultaDto.getId());

        if (ultimaNotificacao != null) {
            return;
        }

        NotificacaoEstoqueCriacaoDto dto = new NotificacaoEstoqueCriacaoDto();
        dto.setNomeProduto(produto.getNome());
        dto.setQuantidade(produtoConsultaDto.getQntdTotalEstoque());
        dto.setNivelEstoque(nivelEstoque);
        NotificacaoEstoque notificacaoEstoque = NotificacaoEstoqueMapper.toEntity(dto);
        notificacaoEstoque.setProduto(produto);
        notificacaoEstoqueRepository.save(notificacaoEstoque);
    }

    public List<NotificacaoEstoqueConsultaDto> buscarNotificacoesNaoExcluidas(Integer idEmpresa) {
        List<NotificacaoEstoqueConsultaDto> notificacaoEstoqueConsultaDto = NotificacaoEstoqueMapper
                .toDto( notificacaoEstoqueRepository.findNotificacoesByEmpresaId(idEmpresa));
        return notificacaoEstoqueConsultaDto;
    }

    public void excluirNotificacao(Integer idNotificacao) {
        NotificacaoEstoque notificacaoEstoque = notificacaoEstoqueRepository.findById(idNotificacao)
                .orElseThrow( () -> new RuntimeException("Notificação não encontrada"));
        notificacaoEstoque.setExcluido(1);
        notificacaoEstoque.setDtExclusao(java.time.LocalDateTime.now());
        notificacaoEstoqueRepository.save(notificacaoEstoque);
    }

    public void marcarComoLida(Integer idNotificacao) {
        NotificacaoEstoque notificacaoEstoque = notificacaoEstoqueRepository.findById(idNotificacao)
                .orElseThrow( () -> new RuntimeException("Notificação não encontrada"));
        notificacaoEstoque.setLido(1);
        notificacaoEstoque.setDtLeitura(java.time.LocalDateTime.now());
        notificacaoEstoqueRepository.save(notificacaoEstoque);
    }

    public void marcarTodasComoLidas(Integer idEmpresa) {
        List<NotificacaoEstoque> notificacoes = notificacaoEstoqueRepository.findNotificacoesByEmpresaId(idEmpresa);
        for (NotificacaoEstoque notificacao : notificacoes) {
            notificacao.setLido(1);
            notificacao.setDtLeitura(java.time.LocalDateTime.now());
        }
        notificacaoEstoqueRepository.saveAll(notificacoes);
    }

    public Boolean validarNotificacoesNaoLidas (Integer idEmpresa) {
        List<NotificacaoEstoque> notificacoes = notificacaoEstoqueRepository.findNotificacoesByEmpresaId(idEmpresa);
        for (NotificacaoEstoque notificacao : notificacoes) {
            if (notificacao.getLido() == 0) {
                return true;
            }
        }
        return false;
    }

}
