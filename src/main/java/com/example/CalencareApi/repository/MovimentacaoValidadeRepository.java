package com.example.CalencareApi.repository;
import com.example.CalencareApi.entity.MovimentacaoValidade;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovimentacaoValidadeRepository extends JpaRepository<MovimentacaoValidade, Integer> {

    // Inativar todas as movimentações de estoque ao inativar/deletar uma validade
    @Modifying
    @Transactional
    @Query("UPDATE MovimentacaoValidade mv SET mv.bitStatus = 0 WHERE mv.validade.id = :id")
    void deleteByValidadeId(Integer id);

    @Query("SELECT mv FROM MovimentacaoValidade mv WHERE mv.validade.id = :id AND mv.bitStatus = 1 ORDER BY mv.dtCriacao DESC")
    List<MovimentacaoValidade> findByValidadeId(Integer id);

    @Query("SELECT mv FROM MovimentacaoValidade mv WHERE mv.id = :id AND mv.bitStatus = 1")
    <Optional>MovimentacaoValidade findByMovId(Integer id);

    @Query("SELECT new map (mv.validade.produto.id AS id_prod,AVG(mv.quantidade) AS qntd) FROM MovimentacaoValidade mv " +
            "WHERE mv.bitStatus = 1 " +
            "AND mv.tipoMovimentacao = 1" +
            "AND mv.validade.produto.empresa.id = :idEmpresa " +
            "GROUP BY mv.validade.produto.id")
    List<Map<String,Object>> findAverageByProdutoId(Integer idEmpresa);

    // retornar quantidade de produtos repostos no dia
    @Query("SELECT COUNT(DISTINCT (mv.validade.produto.id)) FROM MovimentacaoValidade mv " +
            "WHERE mv.bitStatus = 1 " +
            "AND mv.tipoMovimentacao = 1 " +
            "AND mv.dtCriacao BETWEEN :dataInicio AND :dataFim " +
            "AND mv.validade.produto.empresa.id = :idEmpresa")
    Integer findReposicaoByData(Integer idEmpresa, LocalDateTime dataInicio, LocalDateTime dataFim);
}
