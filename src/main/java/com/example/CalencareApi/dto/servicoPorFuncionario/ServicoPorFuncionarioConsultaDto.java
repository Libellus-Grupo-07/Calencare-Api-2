package com.example.CalencareApi.dto.servicoPorFuncionario;

import com.example.CalencareApi.interfaces.ValidaStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServicoPorFuncionarioConsultaDto implements ValidaStatus {
   private Integer id;
   private String nomeFuncionario;
   private String categoria;
   private String nomeServico;
   private Integer duracao;
   private Double preco;
   private Integer funcionarioId;
   private Integer servicoPrecoId;
   private Integer bitStatus;
   private FuncionarioConsultaDto funcionario;
   private ServicoPrecoConsultaDto servicoPreco;

   @Data
   public static class FuncionarioConsultaDto{
       private Integer id;
       private String nome;
       private String telefone;
       private String email;
       private Integer bitStatus;
       private LocalDateTime dtCriacao;
       private String perfilAcesso;
   }

   @Data
    public static class ServicoPrecoConsultaDto implements ValidaStatus{
        private Integer id;
        private String nome;
        private String categoria;
        private Integer duracao;
        private Double preco;
        private Integer bitStatus;
        private LocalDateTime dtCriacao;
    }



    public String getDescricaoStatus() {
        return validaStatus(this.bitStatus);
    }
}
