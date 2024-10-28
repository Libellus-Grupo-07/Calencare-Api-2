package com.example.CalencareApi.dto.cliente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClienteConsultaDto {
    private Integer id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private LocalDateTime dtCriacao;
    private Integer empresaId;

    public String getNomeCompleto() {
        return this.nome + " " + this.sobrenome;
    }
}
