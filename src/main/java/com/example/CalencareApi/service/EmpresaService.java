package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.empresa.*;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.listaObj.Fila;
import com.example.CalencareApi.mapper.EmpresaMapper;
import com.example.CalencareApi.repository.EmpresaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository repository;

    Fila<Integer> fila = new Fila<>(5);

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public EmpresaConsultaDto cadastrarEmpresa(EmpresaCriacaoDto empresaDto) {
        if (Objects.isNull(empresaDto)) {
            return null;
        }
        validarFila();
        Empresa novaEmpresa = EmpresaMapper.toEntity(empresaDto);
        Empresa empresaCadastrada = this.repository.save(novaEmpresa);
        EmpresaConsultaDto empresaCadastradaDto = EmpresaMapper.toDto(empresaCadastrada);
        fila.enqueue(empresaCadastradaDto.getId());
        return empresaCadastradaDto;
    }

    public EmpresaConsultaDto buscarEmpresaPorId(Integer id) {
        Optional<Empresa> empresaBusca = this.repository.findById(id);

        if (empresaBusca.isEmpty()) {
            return null;
        }

        EmpresaConsultaDto empresaBuscaDto = EmpresaMapper.toDto(empresaBusca.get());

        return empresaBuscaDto;
    }

    public Empresa buscarEntidadePorId(Integer id) {
        Optional<Empresa> empresaBusca = this.repository.findById(id);

        if (empresaBusca.isEmpty()) {
            return null;
        }

        return empresaBusca.get();
    }

    public List<EmpresaConsultaDto> listarEmpresas() {
        return EmpresaMapper.toDto(this.repository.findAll());
    }

    public List<EmpresaConsultaDto> pesquisarEmpresaPorRazaoSocial(String razaoSocial) {
        List<Empresa> empresasBusca = this.repository.findAllByRazaoSocialLikeIgnoreCase(razaoSocial);
        return EmpresaMapper.toDto(empresasBusca);
    }

    public EmpresaConsultaDto atualizarEmpresa(Integer id, EmpresaAtualizacaoDto empresaDto) {
        Optional<Empresa> empresaEntity = this.repository.findById(id);

        if (empresaEntity.isEmpty()) {
            return null;
        }

        Empresa empresaAtualizar = empresaEntity.get();
        //empresaAtualizar.setCnpj(empresaDto.getCnpj());
        empresaAtualizar.setRazaoSocial(empresaDto.getRazaoSocial());
        empresaAtualizar.setEmailPrincipal(empresaDto.getEmailPrincipal());
        empresaAtualizar.setTelefonePrincipal(empresaDto.getTelefonePrincipal());
        empresaAtualizar.setIntervaloAtendimento(empresaDto.getIntervaloAtendimento());
        Empresa empresaSalva = this.repository.save(empresaAtualizar);
        EmpresaConsultaDto empresaAtualizadaDto = EmpresaMapper.toDto(empresaSalva);
        return empresaAtualizadaDto;
    }

    public Boolean excluirEmpresaPorId(Integer id) {
        if (!this.existeEmpresaPorId(id)) {
            return false;
        }

        this.repository.deleteById(id);
        return true;
    }

    public Boolean existeCnpj(String cnpj) {
        return this.repository.existsEmpresaByCnpj(cnpj);
    }

    public Boolean existeEmpresaPorId(Integer id) {
        return this.repository.existsEmpresaById(id);
    }

    public void validarFila() {
        if (fila.size() == 5) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Fila cheia");
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void liberarFila() {
        if (fila.isEmpty()) {
            return;
        }
        fila.dequeue();
    }
}
