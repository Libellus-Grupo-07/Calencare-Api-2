package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.NotificacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

@SuppressWarnings("ALL")
public interface NotificacaoEstoqueRepository extends JpaRepository<NotificacaoEstoque, Integer> {

    // validar se já existe notificação para o produto
    @Query(name = "SELECT ne.* FROM notificacao_estoque ne " +
            "INNER JOIN produto p ON ne.produto_id = p.id " +
            "WHERE ne.excluido = 0 " +
            "AND ne.lido = 0 " +
            "AND p.id = :idProduto " +
            "ORDER BY ne.dtCriacao DESC",
            nativeQuery = true)
    NotificacaoEstoque findByProdutoId(Integer idProduto);

    // retornar notificações de estoque
    @Query("SELECT ne FROM NotificacaoEstoque ne " +
            "WHERE ne.excluido = 0 " +
            "AND ne.produto.empresa.id = :idEmpresa " +
            "ORDER BY ne.dtCriacao DESC")
    List<NotificacaoEstoque> findNotificacoesByEmpresaId(Integer idEmpresa);
}
