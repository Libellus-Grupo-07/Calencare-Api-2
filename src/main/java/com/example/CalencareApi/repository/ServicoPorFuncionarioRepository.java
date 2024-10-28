package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Funcionario;
import com.example.CalencareApi.entity.ServicoPorFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoPorFuncionarioRepository extends JpaRepository<ServicoPorFuncionario, Integer>{

    @Query("SELECT s FROM ServicoPorFuncionario s WHERE s.id = :id AND s.funcionario.empresa.id = :idEmpresa")
    ServicoPorFuncionario buscarIdPorEmpresa(Integer id, Integer idEmpresa);

    @Query("SELECT s FROM ServicoPorFuncionario s WHERE s.funcionario.empresa.id = :idEmpresa")
    List<ServicoPorFuncionario> buscarTodosPorEmpresa(Integer idEmpresa);

    @Query("SELECT s FROM ServicoPorFuncionario s WHERE s.funcionario.id = :idFuncionario AND s.funcionario.empresa.id = :idEmpresa")
    List<ServicoPorFuncionario> buscarServicosPorFuncionario(Integer idEmpresa,Integer idFuncionario);

    @Query("SELECT s FROM ServicoPorFuncionario s WHERE s.funcionario.id = :idFuncionario AND s.id = :id")
    ServicoPorFuncionario buscarServicoPorFuncionario(Integer id, Integer idFuncionario);

    @Query("SELECT s FROM ServicoPorFuncionario s WHERE s.funcionario.id = :idFuncionario AND s.servicoPreco.id = :idServicoPreco")
    ServicoPorFuncionario validarSeServicoJaExisteParaFuncionario(Integer idFuncionario, Integer idServicoPreco);

    //Lista de funcionários com serviços alocados
    @Query("SELECT DISTINCT s.funcionario.nome FROM ServicoPorFuncionario s WHERE s.funcionario.empresa.id = :idEmpresa")
    List<String> listarFuncionariosComServicos(Integer idEmpresa);

    @Query("SELECT DISTINCT s.funcionario FROM ServicoPorFuncionario s WHERE s.funcionario.empresa.id = :idEmpresa")
    List<Funcionario> listarFuncionariosComServicosEntidade(Integer idEmpresa);

    @Query("SELECT s.funcionario FROM ServicoPorFuncionario s WHERE s.funcionario.empresa.id = :idEmpresa AND s.servicoPreco.id = :idServicoPreco")
    List<Funcionario> buscarServicoPorEmpresa(Integer idEmpresa, Integer idServicoPreco);
}
