package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.despesa.DespesaAtualizarDto;
import com.example.CalencareApi.dto.despesa.DespesaConsultaDto;
import com.example.CalencareApi.dto.despesa.DespesaCriacaoDto;
import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.entity.Despesa;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.mapper.DespesaMapper;
import com.example.CalencareApi.repository.CategoriaDespesaRepository;
import com.example.CalencareApi.repository.DespesaRepository;
import com.example.CalencareApi.repository.EmpresaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final EmpresaService empresaService;
    private final CategoriaDespesaService categoriaDespesaService;

    public DespesaService(DespesaRepository despesaRepository,
                          EmpresaService empresaService,
                          CategoriaDespesaService  categoriaDespesaService) {
        this.despesaRepository = despesaRepository;
        this.empresaService = empresaService;
        this.categoriaDespesaService = categoriaDespesaService;
    }

    public DespesaConsultaDto cadastrar(DespesaCriacaoDto despesaCriacaoDto,
                                        Integer empresaId,
                                        Integer categoriaId) {
        if (Objects.isNull(despesaCriacaoDto)) {
            return null;
        }
        if (empresaService.buscarEmpresaPorId(empresaId) == null) {
            return null;
        }

        if (categoriaDespesaService.buscarPorId(categoriaId) == null) {
            return null;
        }

        Empresa empresa = empresaService.buscarEntidadePorId(empresaId);
        CategoriaDespesa categoriaDespesa = categoriaDespesaService.buscarEntidadePorId(categoriaId);

        Despesa despesa = DespesaMapper.toEntity(despesaCriacaoDto);
        despesa.setEmpresa(empresa);
        despesa.setCategoriaDespesa(categoriaDespesa);
        Despesa despesaCadastrada = this.despesaRepository.save(despesa);
        DespesaConsultaDto dto = DespesaMapper.toDto(despesaCadastrada);
        return dto;
    }

    public DespesaConsultaDto buscarPorId(Integer id) {
        Optional<Despesa> despesaBusca = this.despesaRepository.findById(id);
        if (despesaBusca.isEmpty()) { return null; }
        DespesaConsultaDto dto = DespesaMapper.toDto(despesaBusca.get());
        return dto;
    }

    /*public List<DespesaConsultaDto> listar() {
        return DespesaMapper.toDto(this.despesaRepository.findAll());
    }*/

    public List<DespesaConsultaDto> listarPorEmpresaId(Integer empresaId) {
        return DespesaMapper.toDto(despesaRepository.findByEmpresaId(empresaId));
    }

    public DespesaConsultaDto atualizarPorEmpresa(Integer idDespesa,
                                                  DespesaAtualizarDto despesaDto,
                                                  Integer empresaId,
                                                  Integer categoriaId) {
        Despesa despesaAtualizacao = this.despesaRepository.findById(idDespesa).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));

        if (empresaService.buscarEmpresaPorId(empresaId) == null) { return null; }
        if (categoriaDespesaService.buscarPorId(categoriaId) == null) { return null; }

        Empresa empresa = empresaService.buscarEntidadePorId(empresaId);
        CategoriaDespesa categoriaDespesa = categoriaDespesaService.buscarEntidadePorId(categoriaId);

        despesaAtualizacao.setNome(
                despesaDto.getNome() == null ? despesaAtualizacao.getNome() : despesaDto.getNome());
        despesaAtualizacao.setObservacao(
                despesaDto.getObservacao() == null ? despesaAtualizacao.getObservacao() : despesaDto.getObservacao());
        despesaAtualizacao.setValor(
                despesaDto.getValor() == null ? despesaAtualizacao.getValor() : despesaDto.getValor());
        despesaAtualizacao.setFormaPagamento(
                despesaDto.getFormaPagamento() == null ? despesaAtualizacao.getFormaPagamento() : despesaDto.getFormaPagamento());
        despesaAtualizacao.setDtPagamento(
                despesaDto.getDtPagamento() == null ? despesaAtualizacao.getDtPagamento() : despesaDto.getDtPagamento());
        despesaAtualizacao.setBitStatus(
                despesaDto.getBitStatus() == null ? despesaAtualizacao.getBitStatus() : despesaDto.getBitStatus());

        return DespesaMapper.toDto(despesaRepository.save(despesaAtualizacao));
    }

    public Despesa retornarEntidadePorId(Integer id) {
        return this.despesaRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));
    }

    public DespesaConsultaDto retornarPorIdPorEmpresa (Integer id, Integer empresaId) {
        Despesa despesa = this.despesaRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));
        Empresa empresa = empresaService.buscarEntidadePorId(empresaId);
        if (!despesa.getEmpresa().equals(empresa)) {
            return null;
        }
        return DespesaMapper.toDto(despesa);
    }

    public void deletarPorId(Integer id, Integer empresaId) {
        Despesa despesa = this.despesaRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));
        Empresa empresa = empresaService.buscarEntidadePorId(empresaId);
        if (!despesa.getEmpresa().equals(empresa)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Despesa não pertence a empresa");
        }
        despesa.setBitStatus(4);
        this.despesaRepository.save(despesa);
    }

    public Double calcularDespesaTotal(Integer empresaId) {
        List<Despesa> despesas = despesaRepository.findByEmpresaId(empresaId);
        double total = 0;
        for (Despesa despesa : despesas) {
            total += despesa.getValor();
        }
        return total;
    }

    public Double calcularDespesaTotalMes(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        List<Despesa> despesas = despesaRepository.encontrarDespesasPeriodo(empresaId, dataInicioTransformada, dataFimTransformada);
        if (despesas.isEmpty()) {
            return 0.0;
        }
        Double total = 0.0;
        for (Despesa despesa : despesas) {
            total += despesa.getValor();
        }
        return total;
    }

    public Double calcularDespesaTotalDia (Integer empresaId, LocalDate data) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusDays(1).minusSeconds(1);
        List<Despesa> despesas = despesaRepository.encontrarDespesasPeriodo(empresaId, dataInicioTransformada, dataFimTransformada);
        if (despesas.isEmpty()) {
            return 0.0;
        }
        Double total = 0.0;
        for (Despesa despesa : despesas) {
            total += despesa.getValor();
        }
        return total;
    }

    public List<DespesaConsultaDto> exibirDespesasDia (Integer empresaId, LocalDate data) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusDays(1).minusSeconds(1);
        List<Despesa> despesas = despesaRepository.encontrarDespesasPeriodo(empresaId, dataInicioTransformada, dataFimTransformada);
        return DespesaMapper.toDto(despesas);
    }

    public List<DespesaConsultaDto> exibirDespesasMes (Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        List<Despesa> despesas = despesaRepository.encontrarDespesasPeriodo(empresaId, dataInicioTransformada, dataFimTransformada);
        return DespesaMapper.toDto(despesas);
    }

    public List<Map<String, Object>> getListaDespesaDiaria(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        return despesaRepository.getDespesaDiaria(empresaId, dataInicioTransformada, dataFimTransformada);
    }

    public Boolean existePorId(Integer id) {
        return this.despesaRepository.existsById(id);
    }
}
