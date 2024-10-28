package com.example.CalencareApi.dto.servicoPreco;

import com.example.CalencareApi.interfaces.ValidaStatus;
import lombok.Data;

@Data
public class ServicoPrecoConsultaDto implements ValidaStatus{
    private Integer id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer duracao;
    private Double comissao;
    private Integer bitStatus;
    private Integer servicoId;
    private String categoria;

    public String getDescricaoStatus() {
        return validaStatus(this.bitStatus);
    }
}



