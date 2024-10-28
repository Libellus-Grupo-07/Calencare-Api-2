package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.servico.ServicoCriacaoDto;
import com.example.CalencareApi.dto.servicoPreco.*;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.Servico;
import com.example.CalencareApi.entity.ServicoPreco;
import com.example.CalencareApi.exceptions.ServicoPrecoNomeJaExisteException;
import com.example.CalencareApi.mapper.ServicoPrecoMapper;
import com.example.CalencareApi.repository.ServicoPrecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicoPrecoService {
    @Autowired private ServicoPrecoRepository repository;
    @Autowired private ServicoService servicoService;
    @Autowired private EmpresaService empresaService;

    public ServicoPrecoConsultaDto buscarPorId(Integer id){
        Optional<ServicoPreco> servicoPreco = this.repository.findById(id);

        if (servicoPreco.isEmpty()){
            return null;
        }
        ServicoPrecoConsultaDto servicoPrecoDto = ServicoPrecoMapper.toDto(servicoPreco.get());
        return servicoPrecoDto;
    }

    public ServicoPreco buscarEntidadePorId(Integer id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    public List<ServicoPrecoConsultaDto> listarTodos(){
        return ServicoPrecoMapper.toDto(this.repository.findAll());
    }

    public ServicoPrecoConsultaDto atualizar(
            Integer id,
            ServicoPrecoAtualizacaoDto servicoDto,
            Integer idCategoria){

        ServicoPreco servicoEntity = this.repository.findById(id).orElse(null);

        Servico buscaNomeServico = servicoService.buscarPorNome(servicoDto.getNome());

        if (servicoEntity == null){
            return null;
        }

        if (Objects.isNull(buscaNomeServico)){
            ServicoCriacaoDto novoServico = new ServicoCriacaoDto();
            novoServico.setNome(servicoDto.getNome());
            novoServico.setCategoriaId(idCategoria);
            buscaNomeServico = servicoService.cadastrar(novoServico, idCategoria);
        }

        /*if (buscaNomeServico.getId() == repository.buscarServicoPrecoByServicoId(buscaNomeServico.getId())){
            throw new ServicoPrecoNomeJaExisteException();
        }*/

        //String nome = (servicoDto.getNome() == null || servicoDto.getNome().isEmpty()) ?  buscaNomeServico.getNome() : servicoDto.getNome();
        String descricao = (servicoDto.getDescricao() == null || servicoDto.getDescricao().isEmpty()) ?
                servicoEntity.getDescricao() : servicoDto.getDescricao();
        Double preco = (servicoDto.getPreco() == null) ? servicoEntity.getPreco() : servicoDto.getPreco();
        Integer duracao = (servicoDto.getDuracao() == null || servicoDto.getDuracao() == 0) ?
                servicoEntity.getDuracao() : servicoDto.getDuracao();
        Double comissao = (servicoDto.getComissao() == null) ?  servicoEntity.getComissao() : servicoDto.getComissao();
        Integer bitStatus = (servicoDto.getBitStatus() == null) ? servicoEntity.getBitStatus() : servicoDto.getBitStatus();

        servicoEntity.setDescricao(descricao);
        servicoEntity.setPreco(preco);
        servicoEntity.setDuracao(duracao);
        servicoEntity.setComissao(comissao);
        servicoEntity.setBitStatus(bitStatus);
        servicoEntity.setServico(buscaNomeServico);

        ServicoPreco servicoPrecoAtualizado = this.repository.save(servicoEntity);
        ServicoPrecoConsultaDto servicoPrecoAtualizadoDto = ServicoPrecoMapper.toDto(servicoPrecoAtualizado);
        return servicoPrecoAtualizadoDto;
    }

    public ServicoPrecoConsultaDto atualizaPrecoServico(Integer id, ServicoPrecoAtualizacaoPrecoDto servicoDto){
        Optional<ServicoPreco> servicoAtualizar = this.repository.findById(id);

        if (servicoAtualizar.isEmpty()){
            return null;
        }

        servicoAtualizar.get().setPreco(servicoDto.getPreco());

        ServicoPreco servicoPrecoAtualizado = this.repository.save(servicoAtualizar.get());
        ServicoPrecoConsultaDto servicoPrecoAtualizadoDto = ServicoPrecoMapper.toDto(servicoPrecoAtualizado);
        return servicoPrecoAtualizadoDto;
    }

    public ServicoPrecoConsultaDto atualizaComissaoServico(Integer id, ServicoPrecoAtualizacaoComissaoDto servicoDto){
        Optional<ServicoPreco> servicoAtualizar = this.repository.findById(id);
        if (servicoAtualizar.isEmpty()){
            return null;
        }
        servicoAtualizar.get().setComissao(servicoDto.getComissao());

        ServicoPreco servicoComissaoAtualizado = this.repository.save(servicoAtualizar.get());
        ServicoPrecoConsultaDto servicoPrecoComissaoAtualizadoDto = ServicoPrecoMapper.toDto(servicoComissaoAtualizado);
        return servicoPrecoComissaoAtualizadoDto;
    }

    public Boolean excluirPorId(Integer id){
        if(this.existePorId(id)){
           return false;
        }
        this.repository.deleteById(id);
        return true;
    }

    public Boolean existePorId(Integer id){
        return this.repository.existsServicoPrecoById(id);
    }

    // Métodos com retorno parametrizado por empresa

    public ServicoPrecoConsultaDto cadastrar(
            ServicoPrecoCriacaoDto servicoPreco,
            Integer idCategoria,
            Integer idEmpresa){

        if (Objects.isNull(servicoPreco)){
            return null;
        }

        Empresa buscaEmpresa = empresaService.buscarEntidadePorId(idEmpresa);

        Servico buscaNomeServico = servicoService.buscarPorNome(servicoPreco.getNome());

        if (Objects.isNull(buscaNomeServico)){
            ServicoCriacaoDto novoServico = new ServicoCriacaoDto();
            novoServico.setNome(servicoPreco.getNome());
            novoServico.setCategoriaId(idCategoria);
            buscaNomeServico = servicoService.cadastrar(novoServico, idCategoria);
        }

        if (buscaNomeServico.getId() == repository.buscarServicoPrecoByServicoId(buscaNomeServico.getId(), idEmpresa)){
            throw new ServicoPrecoNomeJaExisteException();
        }

        ServicoPreco novoServicoPreco = ServicoPrecoMapper.toEntity(servicoPreco);
        novoServicoPreco.setServico(buscaNomeServico);
        novoServicoPreco.setEmpresa(buscaEmpresa);
        ServicoPreco servicoCadastrado = this.repository.save(novoServicoPreco);
        ServicoPrecoConsultaDto servicoCadastradoDto = ServicoPrecoMapper.toDto(servicoCadastrado);
        return servicoCadastradoDto;
    }

    public ServicoPrecoConsultaDto buscarPorId(Integer idEmpresa, Integer id){
        ServicoPreco servicoPreco = this.repository.buscarServicoPrecoByEmpresaId(idEmpresa, id);

        if (servicoPreco == null){
            return null;
        }

        ServicoPrecoConsultaDto servicoPrecoDto = ServicoPrecoMapper.toDto(servicoPreco);
        return servicoPrecoDto;
    }

    public List<ServicoPrecoConsultaDto> listarTodos(Integer idEmpresa){
        return ServicoPrecoMapper.toDto(this.repository.buscarServicoPrecoByEmpresaId(idEmpresa));
    }

    public ServicoPrecoConsultaDto atualizar(
            Integer id,
            Integer idEmpresa,
            ServicoPrecoAtualizacaoDto servicoDto,
            Integer idCategoria){

        ServicoPreco servicoEntity = this.repository.buscarServicoPrecoByEmpresaId(idEmpresa,id);

        if (servicoEntity == null){
            return null;
        }

        Servico buscaNomeServico = servicoService.buscarPorNome(servicoDto.getNome());

        if (Objects.isNull(buscaNomeServico)){
            ServicoCriacaoDto novoServico = new ServicoCriacaoDto();
            novoServico.setNome(servicoDto.getNome());
            novoServico.setCategoriaId(idCategoria);
            buscaNomeServico = servicoService.cadastrar(novoServico, idCategoria);
        }

        /*if (buscaNomeServico.getId() == repository.buscarServicoPrecoByServicoId(buscaNomeServico.getId())){
            throw new ServicoPrecoNomeJaExisteException();
        }*/

        //String nome = (servicoDto.getNome() == null || servicoDto.getNome().isEmpty()) ?  buscaNomeServico.getNome() : servicoDto.getNome();
        String descricao = (servicoDto.getDescricao() == null || servicoDto.getDescricao().isEmpty()) ?
                servicoEntity.getDescricao() : servicoDto.getDescricao();
        Double preco = (servicoDto.getPreco() == null) ? servicoEntity.getPreco() : servicoDto.getPreco();
        Integer duracao = (servicoDto.getDuracao() == null || servicoDto.getDuracao() == 0) ?
                servicoEntity.getDuracao() : servicoDto.getDuracao();
        Double comissao = (servicoDto.getComissao() == null) ?  servicoEntity.getComissao() : servicoDto.getComissao();
        Integer bitStatus = (servicoDto.getBitStatus() == null) ? servicoEntity.getBitStatus() : servicoDto.getBitStatus();

        servicoEntity.setDescricao(descricao);
        servicoEntity.setPreco(preco);
        servicoEntity.setDuracao(duracao);
        servicoEntity.setComissao(comissao);
        servicoEntity.setBitStatus(bitStatus);
        servicoEntity.setServico(buscaNomeServico);

        ServicoPreco servicoPrecoAtualizado = this.repository.save(servicoEntity);
        ServicoPrecoConsultaDto servicoPrecoAtualizadoDto = ServicoPrecoMapper.toDto(servicoPrecoAtualizado);
        return servicoPrecoAtualizadoDto;
    }

    public Boolean excluirPorId(Integer idEmpresa, Integer id){
        ServicoPreco servicoPreco = this.repository.buscarServicoPrecoByEmpresaId(idEmpresa, id);
        if (servicoPreco == null){
            return false;
        }

        servicoPreco.setBitStatus(4);
        this.repository.save(servicoPreco);

        return true;
    }
}
