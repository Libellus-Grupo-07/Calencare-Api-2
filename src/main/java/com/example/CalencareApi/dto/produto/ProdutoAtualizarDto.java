package com.example.CalencareApi.dto.produto;


import com.example.CalencareApi.entity.CategoriaProduto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoAtualizarDto {
    private String nome;
    private String descricao;
    private String marca;
    private Integer categoriaProdutoId;
}
