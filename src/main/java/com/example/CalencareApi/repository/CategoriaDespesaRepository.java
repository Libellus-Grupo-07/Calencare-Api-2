package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.CategoriaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Integer> {
    Boolean existsCategoriaDespesaByNome(String nome);
}
