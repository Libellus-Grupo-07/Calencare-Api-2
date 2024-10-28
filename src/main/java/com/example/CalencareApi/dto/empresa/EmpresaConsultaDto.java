package com.example.CalencareApi.dto.empresa;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter
public class EmpresaConsultaDto {
    private Integer id;
    private String cnpj;
    private String razaoSocial;
    private String emailPrincipal;
    private String telefonePrincipal;
    private LocalDateTime dtCriacao;
    private Integer intervaloAtendimento;
    private List<FuncionarioConsultaDto> funcionarios;
    private List<HorarioFuncionamentoConsultaDto> horariosFuncionamentos;

    @Data
    public static class FuncionarioConsultaDto{
        private Integer id;
        private String nome;
        private String telefone;
        private String email;
        private Integer bitStatus;
        private LocalDateTime dtCriacao;
    }

    @Data
    public static class HorarioFuncionamentoConsultaDto{
        private Integer id;
        private String diaSemana;
        private LocalTime inicio;
        private LocalTime fim;
        private Integer status;
    }
}
