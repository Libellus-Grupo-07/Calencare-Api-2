package com.example.CalencareApi.service;

import com.example.CalencareApi.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.CalencareApi.dto.funcionario.*;

import com.example.CalencareApi.entity.Funcionario;
import com.example.CalencareApi.mapper.FuncionarioMapper;
import com.example.CalencareApi.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public FuncionarioConsultaDto criar(FuncionarioCriacaoDto usuarioCriacaoDto) {
         Funcionario novoUsuario = FuncionarioMapper.of(usuarioCriacaoDto);

        if(funcionarioRepository.existsByEmail(novoUsuario.getEmail())) {
            throw new ResponseStatusException(400, "Email já cadastrado", null);
        }

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.funcionarioRepository.save(novoUsuario);
        return FuncionarioMapper.toDto(novoUsuario);
    }

    public FuncionarioConsultaDto alterar(FuncionarioAtualizacaoDto funcionarioAtualizado, Integer id) {
        Optional<Funcionario> usuario = funcionarioRepository.findById(id);

        if (!funcionarioRepository.existsByEmail(usuario.get().getEmail())) {
            throw new ResponseStatusException(404, "Email do usuário não cadastrado", null);
        }

//        String senhaCriptografada = passwordEncoder.encode(usuario.get().getSenha());
        Funcionario usuarioAtualizado = usuario.get();
        usuarioAtualizado.setNome(funcionarioAtualizado.getNome());
        usuarioAtualizado.setTelefone(funcionarioAtualizado.getTelefone());
        usuarioAtualizado.setEmail(funcionarioAtualizado.getEmail());
        usuarioAtualizado.setPerfilAcesso(funcionarioAtualizado.getPerfilAcesso());
        usuarioAtualizado.setBitStatus(funcionarioAtualizado.getBitStatus());
//        usuarioAtualizado.setSenha(senhaCriptografada);

        this.funcionarioRepository.save(usuarioAtualizado);
        return FuncionarioMapper.toDto(usuarioAtualizado);
    }

    public void deletar(Integer id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new ResponseStatusException(404, "Usuário não encontrado", null);
        }

        this.funcionarioRepository.deleteById(id);
    }

    public FuncionarioConsultaDto buscarPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(404, "Usuário não encontrado", null));

        return FuncionarioMapper.toDto(funcionario);
    }

    public Funcionario buscarEntidadePorId(Integer id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(404, "Usuário não encontrado", null));
    }

    public List<FuncionarioConsultaDto> listar() {
        if (funcionarioRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(404, "Nenhum usuário encontrado", null);
        }
        return FuncionarioMapper.toDto(funcionarioRepository.findAll());
    }

    public FuncionarioTokenDto autenticar(FuncionarioLoginDto funcionarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                funcionarioLoginDto.getEmail(), funcionarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Funcionario funcionarioAutenticado =
                funcionarioRepository.findByEmail(funcionarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return FuncionarioMapper.of(funcionarioAutenticado, token);
    }

    public List<FuncionarioConsultaDto> buscarTodosPorEmpresa(Integer idEmpresa){
        return FuncionarioMapper.toDto(this.funcionarioRepository.buscarTodosPorEmpresa(idEmpresa));
    }

    public List<FuncionarioCsvDto> getCsvFuncionarios() {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return FuncionarioMapper.ofCsv(funcionarios);
    }

    public List<Funcionario> listarAdmin(Integer idEmpresa) {
        return funcionarioRepository.findAllAdmins(idEmpresa);
    }

    public FuncionarioConsultaDto alterarBitStatus(Integer id, FuncionarioAtualizacaoStatusDto funcionarioBitStatus) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(404, "Usuário não encontrado", null));

        funcionario.setBitStatus(funcionarioBitStatus.getBitStatus());
        funcionarioRepository.save(funcionario);

        return FuncionarioMapper.toDto(funcionario);
    }



}
