package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Agendamento;
import com.example.CalencareApi.entity.Cliente;
import com.example.CalencareApi.entity.ServicoPreco;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    List<Agendamento> findByDiaAndServicoPreco(LocalDate dia, ServicoPreco servicoPreco);
    //List<Agendamento> findByDtHoraContaining(String data);
    //List<Agendamento> findByDtHoraContainingAndServicoId(String data, int idServico);

    @Query("SELECT a FROM Agendamento a WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim AND a.bitStatus = 1")
    List<Agendamento> findAllByDtHoraBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim")
    Integer countTotaisAgendamentosDoDia(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim AND a.funcionario.empresa.id = :empresaId")
    Integer countTotaisAgendamentosDoDia(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim, @Param("empresaId") Integer empresaId);

    @Query("SELECT SUM(ap.preco) FROM Agendamento a JOIN a.servicoPreco ap WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim")
    Double calcularPotencialLucroDoDia(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT SUM(ap.preco) FROM Agendamento a JOIN a.servicoPreco ap WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim AND a.funcionario.empresa.id = :empresaId")
    Double calcularPotencialLucroDoDia(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim, @Param("empresaId") Integer empresaId);

    @Query("SELECT a.servicoPreco.servico.nome FROM Agendamento a GROUP BY a.servicoPreco.servico.nome ORDER BY COUNT(a) DESC")
    String getServicoMaisProcurado();

    @Query("SELECT a.servicoPreco.servico.nome FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId GROUP BY a.servicoPreco.servico.nome ORDER BY COUNT(a) DESC")
    String getServicoMaisProcurado(@Param("empresaId") Integer empresaId);

    @Query("SELECT a FROM Agendamento a WHERE a.dtHora >= CURRENT_TIMESTAMP")
    List<Agendamento> getProximosAgendamentos();

    @Query("SELECT a FROM Agendamento a WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim " +
            "AND a.funcionario.empresa.id = :empresaId AND a.bitStatus = 1 ORDER BY a.funcionario.nome ASC")
    List<Agendamento> getProximosAgendamentos(@Param("empresaId") Integer empresaId, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT a FROM Agendamento a WHERE a.bitStatus = 2 AND a.dtHora <= CURRENT_TIMESTAMP")
    List<Agendamento> getAgendamentosEmAndamento();
    @Query("SELECT a FROM Agendamento a WHERE a.bitStatus = 2 AND a.dtHora <= CURRENT_TIMESTAMP AND a.funcionario.empresa.id = :empresaId")
    List<Agendamento> getAgendamentosEmAndamento(@Param("empresaId") Integer empresaId);

    @Query("SELECT a FROM Agendamento a WHERE a.dtHora >= :dataInicio AND a.dtHora < :dataFim AND a.funcionario.empresa.id = :empresaId ORDER BY a.funcionario.nome ASC, a.dtHora ASC")
    List<Agendamento> getAgendamentosDoDia(@Param("dataInicio") LocalDateTime dataInicio,
                                           @Param("dataFim") LocalDateTime dataFim,
                                           @Param("empresaId") Integer empresaId);

    @Query("SELECT a.cliente FROM Agendamento a WHERE a.servicoPreco.empresa.id = :empresaId")
    List<Cliente> buscarClienteNoAgendamentoPorEmpresaId(@Param("empresaId") Integer empresaId);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND (a.bitStatus = 1 OR a.bitStatus = 2 OR a.bitStatus = 3 OR a.bitStatus = 5) AND CAST(a.dtHora AS date) = :date")
    Integer getTotalAgendamentos(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date AND a.bitStatus = 5")
    Integer getAgendamentosStatusFinalizado(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date AND a.bitStatus = 3")
    Integer getAgendamentosStatusCancelado(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date AND a.bitStatus = 2")
    Integer getAgendamentosStatusPendente(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT SUM(a.servicoPreco.preco) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date AND a.bitStatus = 5")
    Double getLucroTotalDoDia(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date AND a.bitStatus = 5")
    Integer getAgendamentosConfirmadosDoDia(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT new map(a.funcionario.nome as nome, COUNT(a) as count) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date GROUP BY a.funcionario.nome")
    List<Map<String, Object>> getAgendamentosDoDiaPorProfissional(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    @Query("SELECT new map(a.servicoPreco.servico.nome as servico, COUNT(a) as count, SUM(a.servicoPreco.preco) as rentabilidade) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date GROUP BY a.servicoPreco.servico.nome ORDER BY COUNT(a) DESC")
    List<Map<String, Object>> getServicoMaisProcuradoERentabilidadeDoDia(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT new map(a.servicoPreco.servico.nome as servico, COUNT(a) as count) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date GROUP BY a.servicoPreco.servico.nome ORDER BY COUNT(a) DESC")
    List<Map<String, Object>> getTop3Servicos(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT new map(a.funcionario.nome as profissional, COUNT(a) as count) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date GROUP BY a.funcionario.nome ORDER BY COUNT(a) DESC")
    List<Map<String, Object>> getTop3Profissionais(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT new map(a.cliente.nome as cliente, COUNT(a) as count) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date GROUP BY a.cliente.nome ORDER BY COUNT(a) DESC")
    List<Map<String, Object>> getTop3Clientes(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT new map(a.servicoPreco.servico.categoria.nome as categoria, COUNT(a) as count) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND CAST(a.dtHora AS date) = :date GROUP BY a.servicoPreco.servico.categoria.nome ORDER BY COUNT(a) DESC")
    List<Map<String, Object>> getAgendamentosPorCategoria(@Param("empresaId") Integer empresaId, @Param("date") LocalDate date);

    List<Agendamento> findByFuncionarioEmpresaIdAndDtHoraBetween(Integer empresaId, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT a FROM Agendamento a WHERE a.bitStatus = 2 AND a.funcionario.empresa.id = :empresaId")
    List<Agendamento> getAgendamentosPendentesEmpresa (Integer empresaId);

    @Query("SELECT a FROM Agendamento a WHERE a.id = :id AND a.funcionario.empresa.id = :empresaId")
    Agendamento getAgendamentoPorIdEmpresa(@Param("empresaId") Integer empresaId, @Param("id") Integer id);

    List<Agendamento> findByFuncionarioIdAndDtHoraBetweenOrderByDtHora(Integer funcionarioId, LocalDateTime dataInicio, LocalDateTime dataFim);

   /* Integer countByFuncionarioIdAndDtHoraBetween(Integer funcionarioId, LocalDateTime dataInicio, LocalDateTime dataFim);*/
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.id = :funcionarioId AND a.dtHora BETWEEN :dataInicio AND :dataFim AND a.bitStatus = 5")
    Integer countByFuncionarioIdAndDtHoraBetweenAndBitStatus(Integer funcionarioId, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT COUNT(DISTINCT a.cliente) FROM Agendamento a WHERE a.funcionario.id = :funcionarioId AND a.dtHora BETWEEN :dataInicio AND :dataFim")
    Integer countDistinctClienteByFuncionarioIdAndDtHoraBetween(Integer funcionarioId, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Query("SELECT SUM(a.servicoPreco.preco * a.servicoPreco.comissao) FROM Agendamento a WHERE a.funcionario.id = :funcionarioId AND a.dtHora BETWEEN :dataInicio AND :dataFim AND a.bitStatus = 5")
    Double sumComissaoByFuncionarioIdAndDtHoraBetween(Integer funcionarioId, LocalDateTime dataInicio, LocalDateTime dataFim);

    @Modifying
    @Query("UPDATE Agendamento a SET a.bitStatus = 3 WHERE a.id = :agendamentoId AND a.funcionario.empresa.id = :empresaId")
    void updateBitStatus(Integer agendamentoId, Integer empresaId);

    @Query("SELECT COUNT(DISTINCT a.funcionario.id) FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND a.dtHora BETWEEN :dataInicio AND :dataFim")
    Integer countDistinctFuncionarioByFuncionarioIdAndDtHoraBetween(Integer empresaId, LocalDateTime dataInicio, LocalDateTime dataFim);

    //Buscar histórico do mês
    @Query("SELECT a FROM Agendamento a WHERE a.funcionario.empresa.id = :empresaId AND a.dtHora BETWEEN :dataInicio AND :dataFim")
    List<Agendamento> getHistoricoAgendamentosMes(@Param("empresaId") Integer empresaId,
                                               @Param("dataInicio") LocalDateTime dataInicio,
                                               @Param("dataFim") LocalDateTime dataFim);

    //Somar a receita diária agrupada por dia
    @Query("SELECT new map(CAST(a.dtHora AS date) as data, " +
            "SUM(a.servicoPreco.preco) as total) " +
            "FROM Agendamento a " +
            "WHERE a.funcionario.empresa.id = :empresaId " +
            "AND a.dtHora BETWEEN :dataInicio AND :dataFim " +
            "AND a.bitStatus = 5 " +
            "GROUP BY CAST(a.dtHora AS date)")
    List<Map<String, Object>> getReceitaDiaria(@Param("empresaId") Integer empresaId,
                                               @Param("dataInicio") LocalDateTime dataInicio,
                                               @Param("dataFim") LocalDateTime dataFim);
}
