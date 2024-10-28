package com.example.CalencareApi.dto.funcionario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FuncionarioAtualizacaoDto {
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
    private String perfilAcesso;

    @PositiveOrZero
    private Integer bitStatus;
}
