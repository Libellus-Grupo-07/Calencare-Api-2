package com.example.CalencareApi.dto.endereco;

import com.example.CalencareApi.entity.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoAtualizacaoDto {
    @NotBlank private String cep;
    @NotBlank private String logradouro;
    @NotBlank private String numero;
    private String complemento;
    @NotBlank private String bairro;
    @NotBlank private String localidade;
    @NotBlank private String uf;
    @NotNull private Empresa empresa;
}
