package com.example.CalencareApi.dto.categoria.despesa;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDespesaAtualizarDto {
    @NotBlank
    private String nome;
}
