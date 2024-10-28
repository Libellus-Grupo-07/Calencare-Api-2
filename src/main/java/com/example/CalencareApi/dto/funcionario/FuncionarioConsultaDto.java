package com.example.CalencareApi.dto.funcionario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FuncionarioConsultaDto {
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private Integer bitStatus;
    private LocalDateTime dtCriacao;
    private EmpresaConsulta empresa;
    private String perfilAcesso;

    @Data
    public static class EmpresaConsulta{
        private Integer id;
        private String cnpj;
        private String razaoSocial;
        private String emailPrincipal;
        private String telefonePrincipal;
    }
}
