package com.example.CalencareApi.dto.empresa;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaAtualizacaoDto {
    @NotBlank
    private String razaoSocial;
//    @NotBlank
//    private String cnpj;
    @NotBlank
    @Email
    private String emailPrincipal;
    @NotBlank
    private String telefonePrincipal;
    @NotNull
    @Positive
    private Integer intervaloAtendimento;
}
