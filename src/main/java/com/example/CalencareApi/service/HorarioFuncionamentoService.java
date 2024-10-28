package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.horarioFuncionamento.HorarioFuncionamentoConsultaDto;
import com.example.CalencareApi.dto.horarioFuncionamento.HorarioFuncionamentoCriacaoDto;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.HorarioFuncionamento;
import com.example.CalencareApi.mapper.HorarioFuncionamentoMapper;
import com.example.CalencareApi.repository.HorarioFuncionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class HorarioFuncionamentoService {

    @Autowired HorarioFuncionamentoRepository repository;
    @Autowired EmpresaService empresaService;

    public HorarioFuncionamentoConsultaDto criarHorarioFuncionamento(HorarioFuncionamentoCriacaoDto dto) {
        if (dto.getInicio().isAfter(dto.getFim())) {
            throw new IllegalArgumentException("O horário de início não pode ser maior que o horário de fim");
        }
        Empresa empresa = empresaService.buscarEntidadePorId(dto.getEmpresaId());
        if (empresa == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Empresa não encontrada");
        }

        HorarioFuncionamento horario = HorarioFuncionamentoMapper.toEntity(dto);
        horario.setEmpresa(empresa);
        horario = repository.save(horario);
        return HorarioFuncionamentoMapper.toDto(horario);
    }

    public List<HorarioFuncionamentoConsultaDto> listar() {
        List<HorarioFuncionamento> horarios = repository.findAll();
        if (horarios.isEmpty()) {
            throw new RuntimeException("Nenhum horário de funcionamento encontrado");
            }
        return HorarioFuncionamentoMapper.toDto(horarios);
    }

    public HorarioFuncionamentoConsultaDto horarioPorId(Integer id) {
        Optional<HorarioFuncionamento> horario = repository.findById(id);
        return horario.map(HorarioFuncionamentoMapper::toDto).orElse(null);
    }

    public HorarioFuncionamentoConsultaDto atualizarHorarioFuncionamento(Integer id, HorarioFuncionamentoCriacaoDto dto) {
        HorarioFuncionamento horario = repository.findById(id)
                .map(existingHorario -> {
                    existingHorario.setDiaSemana(dto.getDiaSemana());
                    existingHorario.setInicio(dto.getInicio());
                    existingHorario.setFim(dto.getFim());
                    existingHorario.setStatus(dto.getStatus());
                    //existingHorario.setEmpresa(dto.getEmpresa());
                    return repository.save(existingHorario);
                }).orElseThrow(() -> new RuntimeException("Horario not found!"));
        return HorarioFuncionamentoMapper.toDto(horario);
    }

    public void deleteHorarioFuncionamento(Integer id) {
        repository.deleteById(id);
    }

    public List<HorarioFuncionamentoConsultaDto> buscarHorariosPorIntervalo(LocalTime inicio, LocalTime fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Os horários de início e fim são obrigatórios");
        } else if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("O horário de início não pode ser maior que o horário de fim");
        }else{

        List<HorarioFuncionamento> horarios = repository.findByInicioGreaterThanEqualAndFimLessThanEqual(inicio, fim);
        return horarios.stream()
                .map(HorarioFuncionamentoMapper::toDto)
                .collect(Collectors.toList());
        }
    }

    public List<LocalTime> retornarIntervalosAtendimento(Integer idEmpresa, LocalDate data) {
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        Integer codDia = data.getDayOfWeek().getValue();
        HorarioFuncionamento horario = repository.getHorarioFuncionamentoDiaSemana(codDia, empresa);

        if (horario == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Houve um erro ao buscar o horário de funcionamento da empresa");
        } else

        if (horario.getStatus() == 0) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Empresa fechada neste dia");
        }

        LocalTime horarioAbertura = horario.getInicio();
        List<LocalTime> intervalos = new ArrayList<>();
        LocalTime ultimoHorario = horario.getInicio();

        while (ultimoHorario.isBefore(horario.getFim())) {
            intervalos.add(horarioAbertura);
            horarioAbertura = horarioAbertura.plusMinutes(empresa.getIntervaloAtendimento());
            ultimoHorario = ultimoHorario.plusMinutes(empresa.getIntervaloAtendimento());
        }

        return intervalos;
    }
}
