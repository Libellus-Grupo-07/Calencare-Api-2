package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.ServicoPreco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoPrecoRepository extends JpaRepository<ServicoPreco, Integer> {
    Boolean existsServicoPrecoById(Integer id);

    @Query("SELECT sp.servico.id FROM ServicoPreco sp " +
            "WHERE sp.servico.id = :id AND sp.bitStatus != 4 AND sp.empresa.id = :idEmpresa")
    Integer buscarServicoPrecoByServicoId(Integer id, Integer idEmpresa);

    // Parametrização por empresa
    @Query("SELECT sp FROM ServicoPreco sp WHERE sp.empresa.id = :idEmpresa AND sp.id = :idServicoPreco AND sp.bitStatus != 4" )
    ServicoPreco buscarServicoPrecoByEmpresaId(Integer idEmpresa, Integer idServicoPreco);

    @Query("SELECT sp FROM ServicoPreco sp WHERE sp.empresa.id = :idEmpresa AND sp.bitStatus != 4")
    List<ServicoPreco> buscarServicoPrecoByEmpresaId(Integer idEmpresa);

}
