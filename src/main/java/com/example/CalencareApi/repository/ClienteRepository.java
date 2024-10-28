package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    Optional<Cliente> findById (Integer id);

    Boolean existsByEmail(String email);

    //Buscar clientes por empresa
    @Query("SELECT c FROM Cliente c WHERE c.empresa.id = :id AND c.bitStatus = 1")
    List<Cliente> findByEmpresaId(Integer id);

    //Buscar cliente por empresa
    @Query("SELECT c FROM Cliente c WHERE c.empresa.id = :idEmpresa AND c.id = :idCliente AND c.bitStatus = 1")
    Cliente findByEmpresaIdAndId(Integer idEmpresa, Integer idCliente);
}
