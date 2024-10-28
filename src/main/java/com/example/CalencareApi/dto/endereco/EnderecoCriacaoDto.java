package com.example.CalencareApi.dto.endereco;

import com.example.CalencareApi.entity.Empresa;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoCriacaoDto {
    @NotBlank
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    @NotBlank
    private String numero;
    private Empresa empresa;
}
