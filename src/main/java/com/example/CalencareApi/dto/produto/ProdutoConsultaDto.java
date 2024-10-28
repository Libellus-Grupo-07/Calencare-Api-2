package com.example.CalencareApi.dto.produto;

import com.example.CalencareApi.entity.CategoriaProduto;
import com.example.CalencareApi.entity.Empresa;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoConsultaDto {
    private Integer id;
    private String nome;
    private String descricao;
    private String marca;
    private Integer categoriaProdutoId;
    private String categoriaProdutoNome;
    private Integer empresaId;
}
