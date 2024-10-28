package com.example.CalencareApi.dto.funcionario;

import com.example.CalencareApi.entity.Empresa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioCriacaoDto {

    @Size(min = 3, max = 50)
    @Schema(description = "Nome do usuário", example = "Reis")
    private String nome;

    @NotBlank
    //@Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}$", message = "Número de celular inválido.")
    private String telefone;

    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "rafael.reis@sptech.school")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

    @NotBlank
    private String perfilAcesso;

    @NotNull
    private Empresa empresa;

}
