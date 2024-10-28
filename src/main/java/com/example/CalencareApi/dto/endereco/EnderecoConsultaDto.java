package com.example.CalencareApi.dto.endereco;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoConsultaDto {
    private Integer id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String numero;
    private EmpresaConsultaDto empresa;

    public String getDescricaoEndereco(){
        return String.format("%s, %s%s - %s, %s - %S, %s",
            logradouro,
            numero,
            complemento.isBlank() ? "" : ", " + complemento,
            bairro,
            localidade,
            uf,
            cep
        );
    }

    @Data
    public static class EmpresaConsultaDto{
        private Integer id;
        private String cnpj;
        private String razaoSocial;
        private String emailPrincipal;
        private String telefonePrincipal;
    }
}
