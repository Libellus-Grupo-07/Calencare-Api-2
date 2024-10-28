package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Integer>{

    Servico findByNomeContainingIgnoreCase(String nome);

}
