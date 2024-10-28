package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Integer> {
    Boolean existsCategoriaProdutoByNome(String nome);
}
