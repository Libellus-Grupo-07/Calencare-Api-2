package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Validade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ValidadeRepository extends JpaRepository<Validade, Integer> {

    @Query("SELECT v FROM Validade v WHERE v.produto.id = :idProduto AND v.bitStatus = 1 ORDER BY v.dtValidade")
    List<Validade> findByProdutoId(Integer idProduto);

    @Query("SELECT v from Validade v WHERE v.bitStatus = 1 AND v.id = :id")
    Optional<Validade> findById(Integer id);
}
