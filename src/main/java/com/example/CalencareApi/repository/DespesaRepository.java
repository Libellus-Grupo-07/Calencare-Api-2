package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
    Boolean existsDespesaByNome(String nome);

    @Query("SELECT d FROM Despesa d WHERE d.empresa.id = :id ORDER BY d.dtPagamento DESC")
    List<Despesa> findByEmpresaId (Integer id);

    @Query("SELECT d FROM Despesa d WHERE d.empresa.id = :id AND d.bitStatus = 1 AND d.dtPagamento BETWEEN :dataInicio AND :dataFim")
    List<Despesa> encontrarDespesasPeriodo(Integer id, LocalDateTime dataInicio, LocalDateTime dataFim);

    //Soma diária de despesas agrupada por dia
    @Query("SELECT new map(CAST(d.dtPagamento AS date) as data, " +
            "SUM(d.valor) as total) " +
            "FROM Despesa d " +
            "WHERE d.empresa.id = :empresaId " +
            "AND d.dtPagamento BETWEEN :dataInicio AND :dataFim " +
            "AND d.bitStatus = 1 " +
            "GROUP BY CAST(d.dtPagamento AS date)")
    List<Map<String, Object>> getDespesaDiaria(@Param("empresaId") Integer empresaId,
                                               @Param("dataInicio") LocalDateTime dataInicio,
                                               @Param("dataFim") LocalDateTime dataFim);


    //Soma semanal de despesas agrupada por semana
    @Query(value = "select WEEKOFYEAR(DATE(d.dt_pagamento)) semana, SUM(d.valor) valor FROM DESPESA d" +
            "    INNER JOIN empresa e ON e.id = d.empresa_id" +
            "    WHERE e.id = :empresaId" +
            "    AND DATE(d.dt_pagamento) BETWEEN :dataInicio AND :dataFim" +
            "    AND d.bit_status = 1" +
            "    GROUP BY WEEKOFYEAR(DATE(d.dt_pagamento))"
    , nativeQuery = true)
    List<Map<String, Object>> getTotalDespesasPorSemana(@Param("empresaId") Integer empresaId,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);

    // Lista de despesas do dia
    @Query("SELECT d FROM Despesa d WHERE d.empresa.id = :empresaId AND CAST(d.dtPagamento AS date) = :data AND d.bitStatus = 1 ORDER BY d.dtCriacao")
    List<Despesa> getDespesasDia(@Param("empresaId") Integer empresaId, @Param("data") LocalDate data);
}
