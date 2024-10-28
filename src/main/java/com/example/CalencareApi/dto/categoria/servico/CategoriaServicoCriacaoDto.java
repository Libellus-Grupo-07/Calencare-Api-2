package com.example.CalencareApi.dto.categoria.servico;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaServicoCriacaoDto {
    @NotBlank private String nome;
    @NotBlank private String descricao;
}
