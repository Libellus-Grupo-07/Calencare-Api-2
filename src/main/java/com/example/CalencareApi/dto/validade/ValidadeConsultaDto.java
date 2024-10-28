package com.example.CalencareApi.dto.validade;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ValidadeConsultaDto {
    Integer id;
    String descricao;
    String dtValidade;
    Integer bitStatus;
    Integer produtoId;
}
