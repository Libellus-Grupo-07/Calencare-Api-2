package com.example.CalencareApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class MovimentosConsultaDto {
    Double total;
    Date data;
    String descricao;
}
