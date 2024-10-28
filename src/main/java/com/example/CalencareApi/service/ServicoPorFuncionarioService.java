package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.funcionario.FuncionarioConsultaDto;
import com.example.CalencareApi.dto.servicoPorFuncionario.ServicoPorFuncionarioConsultaDto;
import com.example.CalencareApi.dto.servicoPorFuncionario.ServicoPorFuncionarioCriacaoDto;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.Funcionario;
import com.example.CalencareApi.entity.ServicoPorFuncionario;
import com.example.CalencareApi.entity.ServicoPreco;
import com.example.CalencareApi.exceptions.EntidadeJaExisteException;
import com.example.CalencareApi.exceptions.EntidadesNaoRelacionadasException;
import com.example.CalencareApi.mapper.FuncionarioMapper;
import com.example.CalencareApi.mapper.ServicoPorFuncionarioMapper;
import com.example.CalencareApi.repository.ServicoPorFuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicoPorFuncionarioService {
    @Autowired private ServicoPorFuncionarioRepository repository;
    @Autowired private ServicoPrecoService servicoService;
    @Autowired private FuncionarioService funcionarioService;
    @Autowired private EmpresaService empresaService;

    public ServicoPorFuncionarioConsultaDto cadastrar(Integer idEmpresa,
                                                      ServicoPorFuncionarioCriacaoDto servico
                                                      ){
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        Funcionario funcionario = funcionarioService.buscarEntidadePorId(servico.getIdFuncionario());
        ServicoPreco servicoPreco = servicoService.buscarEntidadePorId(servico.getIdServicoPreco());

        if (empresa == null || funcionario == null || servicoPreco == null) {
            throw new EntidadesNaoRelacionadasException();
        }

        if (servicoPreco.getEmpresa().getId() != idEmpresa) {
            throw new EntidadesNaoRelacionadasException();
        }

        if (funcionario.getEmpresa().getId() != idEmpresa) {
            throw new EntidadesNaoRelacionadasException();
        }

        ServicoPorFuncionario existeServico = this.repository.validarSeServicoJaExisteParaFuncionario(funcionario.getId(), servicoPreco.getId());
        if (existeServico != null) {
            throw new EntidadeJaExisteException();
        }

        ServicoPorFuncionario servicoEntity = new ServicoPorFuncionario();
        servicoEntity.setFuncionario(funcionario);
        servicoEntity.setServicoPreco(servicoPreco);
        servicoEntity.setDtAdd(LocalDateTime.now());
        servicoEntity.setBitStatus(1);

        return ServicoPorFuncionarioMapper.toDto(this.repository.save(servicoEntity));
    }

    public ServicoPorFuncionario buscarEntidadePorId(Integer id){
        return this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    public ServicoPorFuncionarioConsultaDto buscarPorId(Integer id, Integer idEmpresa){
        ServicoPorFuncionario servico = this.repository.buscarIdPorEmpresa(id, idEmpresa);
        if (servico == null) {
            return null;
        }
        return ServicoPorFuncionarioMapper.toDto(servico);
    }

    public List<ServicoPorFuncionarioConsultaDto> buscarTodosPorEmpresa(Integer idEmpresa){
        return ServicoPorFuncionarioMapper.toDto(this.repository.buscarTodosPorEmpresa(idEmpresa));
    }

    public List<ServicoPorFuncionarioConsultaDto> buscarServicoPorFuncionario(Integer idEmpresa,Integer idFuncionario){
        return ServicoPorFuncionarioMapper.toDto(this.repository.buscarServicosPorFuncionario(idEmpresa,idFuncionario));
    }

    public List<String> listarFuncionariosComServicos(Integer idEmpresa){
        return this.repository.listarFuncionariosComServicos(idEmpresa);
    }


    public List<Funcionario> listarFuncionariosComServicosEntidade(Integer idEmpresa){
        return this.repository.listarFuncionariosComServicosEntidade(idEmpresa);
    }

    public ServicoPorFuncionarioConsultaDto alterarStatus(Integer idEmpresa,Integer idFuncionario, Integer id){
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        if (empresa == null) {
            throw new RuntimeException("Empresa não encontrada");
        }

        ServicoPorFuncionario servico = this.repository.buscarServicoPorFuncionario(id, idFuncionario);
        if (servico == null) {
            throw new RuntimeException("Serviço não encontrado");
        }
        if (servico.getBitStatus() == 1) {
            servico.setBitStatus(0);
        } else if (servico.getBitStatus() == 0) {
            servico.setBitStatus(1);
        }
        return ServicoPorFuncionarioMapper.toDto(this.repository.save(servico));
    }

    public void deletar(Integer idEmpresa, Integer id){
        ServicoPorFuncionario servico = this.repository.buscarIdPorEmpresa(id, idEmpresa);
        if (servico == null) {
            throw new RuntimeException("Serviço não encontrado");
        }
        servico.setBitStatus(4);
    }

    public List<FuncionarioConsultaDto> buscarServicoPorEmpresa(Integer idEmpresa, Integer idServicoPreco){

         List<Funcionario> servicosListados = this.repository.buscarServicoPorEmpresa(idEmpresa, idServicoPreco);
         List<FuncionarioConsultaDto> servicosConvertidos = FuncionarioMapper.toDto(servicosListados);

         if (servicosConvertidos.isEmpty()) {
             throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum serviço encontrado");
         }

         return servicosConvertidos;
    }


}
