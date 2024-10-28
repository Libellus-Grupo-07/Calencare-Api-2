package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {


    Optional<Empresa> findByCnpj(String cnpj);

    List<Empresa> findAllByRazaoSocialLikeIgnoreCase(String razaoSocial);
    Boolean existsEmpresaByCnpj(String cnpj);
    Boolean existsEmpresaById(Integer id);
}
