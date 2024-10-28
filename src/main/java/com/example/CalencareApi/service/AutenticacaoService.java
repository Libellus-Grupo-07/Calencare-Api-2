package com.example.CalencareApi.service;

import com.example.CalencareApi.entity.Funcionario;
import com.example.CalencareApi.repository.FuncionarioRepository;
import com.example.CalencareApi.dto.funcionario.FuncionarioDetalhesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(username);

        if (funcionarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("Usuário %s não encontrado",username));
        }
        return new FuncionarioDetalhesDto(funcionarioOpt.get());
    }
}