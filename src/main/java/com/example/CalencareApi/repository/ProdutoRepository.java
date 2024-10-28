package com.example.CalencareApi.repository;
import com.example.CalencareApi.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p WHERE p.id = :id AND p.empresa.id = :idEmpresa AND p.bitStatus = 1")
    Produto findByIdAndEmpresaId(Integer id, Integer idEmpresa);

    //buscar por palavras contidas em nome, marca
    @Query("SELECT p FROM Produto p " +
            "WHERE p.empresa.id = :idEmpresa " +
            "AND p.bitStatus = 1 " +
            "AND ((UPPER(p.nome) LIKE UPPER(CONCAT('%', :nome, '%')) OR UPPER(p.marca) LIKE UPPER(CONCAT('%', :nome, '%'))))")
    List<Produto> findByNomeOrMarca(Integer idEmpresa, String nome);

    //buscar por todos os produtos de uma empresa por categoria,
    @Query("SELECT p FROM Produto p " +
            "WHERE p.empresa.id = :idEmpresa " +
            "AND p.bitStatus = 1 " +
            "ORDER BY p.categoriaProduto.nome, p.nome")
    List<Produto> findAllByEmpresaId(Integer idEmpresa);
}
