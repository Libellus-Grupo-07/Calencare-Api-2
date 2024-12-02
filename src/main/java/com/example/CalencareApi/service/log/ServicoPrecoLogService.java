package com.example.CalencareApi.service.log;

import com.example.CalencareApi.dto.log.ServicoPrecoLogConsultaDto;
import com.example.CalencareApi.dto.log.ServicoPrecoLogCriacaoDto;
import com.example.CalencareApi.entity.ServicoPreco;
import com.example.CalencareApi.entity.log.ServicoPrecoLog;
import com.example.CalencareApi.mapper.log.ServicoPrecoLogMapper;
import com.example.CalencareApi.repository.ServicoPrecoRepository;
import com.example.CalencareApi.repository.log.ServicoPrecoLogRepository;
import com.example.CalencareApi.service.ServicoPrecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

@Service
public class ServicoPrecoLogService {

    @Autowired
    ServicoPrecoLogRepository servicoPrecoLogRepository;
    @Autowired
    ServicoPrecoRepository servicoPrecoRepository;

    public ServicoPrecoLogConsultaDto buscarUltimaAlteracao(Integer servicoPrecoId, LocalDateTime data) {
        return servicoPrecoLogRepository.buscarLog(servicoPrecoId, data)
                .map(ServicoPrecoLogMapper::toDto)
                .orElse(null);
    }

    public ServicoPrecoLogConsultaDto salvar(ServicoPrecoLogCriacaoDto dto) {
        ServicoPreco servicePreco = servicoPrecoRepository.findById(dto.getServicoPrecoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ServicoPrecoLog entity = ServicoPrecoLogMapper.toEntity(dto);
        entity.setServicoPreco(servicePreco);
        return ServicoPrecoLogMapper.toDto(servicoPrecoLogRepository.save(entity));
    }
}
