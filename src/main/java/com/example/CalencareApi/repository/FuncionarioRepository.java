package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findById (Integer id);

    Optional<Funcionario> findByEmpresaId (Integer id);

    Optional<Funcionario> findByEmail (String email);

    @Query("SELECT f FROM Funcionario f WHERE f.perfilAcesso = UPPER('Administrador') AND f.bitStatus NOT IN(4) AND f.empresa.id = :idEmpresa")
    List<Funcionario> findAllAdmins(Integer idEmpresa);

    Optional<Funcionario> findByNome (String nome);

    Boolean existsByEmail(String email);

    @Query("SELECT f FROM Funcionario f WHERE f.empresa.id = :id AND f.bitStatus NOT IN(4)")
    List<Funcionario> buscarTodosPorEmpresa(@Param("id") Integer idEmpresa);

}
