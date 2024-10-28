package com.example.CalencareApi.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaCriacaoDto {
    @NotBlank
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    @NotNull
    private String emailPrincipal;
    @NotNull
    private String telefonePrincipal;
    @NotNull
    private Integer intervaloAtendimento;
}
