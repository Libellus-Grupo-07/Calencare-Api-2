package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.HorarioFuncionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface HorarioFuncionamentoRepository extends JpaRepository<HorarioFuncionamento, Integer> {

List<HorarioFuncionamento> findByInicioGreaterThanEqualAndFimLessThanEqual(LocalTime inicio, LocalTime fim);
List<HorarioFuncionamento> findByEmpresaEquals(Empresa empresa);


@Query("SELECT hf FROM HorarioFuncionamento hf WHERE hf.codDiaSemana = :cod AND hf.empresa = :empresa")
HorarioFuncionamento getHorarioFuncionamentoDiaSemana(Integer cod, Empresa empresa);
}
