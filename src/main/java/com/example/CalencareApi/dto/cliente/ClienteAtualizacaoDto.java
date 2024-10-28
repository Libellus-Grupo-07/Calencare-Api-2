package com.example.CalencareApi.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteAtualizacaoDto {
    @Size(min = 3, max = 25)
    private String nome;
    @Size(min = 3, max = 25)
    private String sobrenome;
    @Size(min = 11, max = 11)
    private String telefone;
    @Email
    private String email;
}
