package com.example.CalencareApi.dto.horarioFuncionamento;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class HorarioFuncionamentoConsultaDto {
    private Integer id;
    private String diaSemana;
    private Integer codDiaSemana;
    private LocalTime inicio;
    private LocalTime fim;
    private Integer status;
    //private EmpresaConsultaDto empresa;

    @Data
    public static class EmpresaConsultaDto{
        private Integer id;
        private String cnpj;
        private String razaoSocial;
        private String emailPrincipal;
        private String telefonePrincipal;
        private LocalDateTime dtCriacao;
    }
}
