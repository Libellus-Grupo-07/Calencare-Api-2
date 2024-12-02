package com.example.CalencareApi.repository.log;

import com.example.CalencareApi.entity.log.ServicoPrecoLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ServicoPrecoLogRepository extends JpaRepository<ServicoPrecoLog, Integer> {

    @SuppressWarnings("SqlResolve")
    @Query(value = "SELECT * FROM servico_preco_log spl \n" +
            "    INNER JOIN servico_preco sp on sp.id = spl.servico_preco_id\n" +
            "    WHERE sp.id = 3\n" +
            "    AND spl.dt_criacao <= '2024-11-25'\n" +
            "    ORDER BY spl.dt_criacao DESC\n" +
            "    LIMIT 1;"
            , nativeQuery = true)
    Optional<ServicoPrecoLog> buscarLog(Integer servicoPrecoId, LocalDateTime data);
}
