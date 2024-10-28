package com.example.CalencareApi.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteCriacaoDto {
    @NotBlank @Size(min = 3, max = 25)
    private String nome;
    @NotBlank @Size(min = 1, max = 25)
    private String sobrenome;
    @NotBlank @Size(min = 11, max = 11)
    private String telefone;
    @Email
    private String email;
    @NotNull
    private Integer empresaId;
}
