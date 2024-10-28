package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
    Optional<Endereco> findByEmpresaId(Integer id);

}
