package com.example.CalencareApi.dto.categoria.despesa;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaDespesaCriacaoDto {
    @NotBlank
    private String nome;
}
