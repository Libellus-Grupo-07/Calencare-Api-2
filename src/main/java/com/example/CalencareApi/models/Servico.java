package com.example.CalencareApi.models;

public class Servico {
    private Integer id;
    private String nome;
    private String descricao;
    private Double valor;
    private Integer duracao;
    private Double porcentComissao;
    private Boolean bitStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getvalor() {
        return valor;
    }

    public void setvalor(Double valor) {
        this.valor = valor;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Double getPorcentComissao() {
        return porcentComissao;
    }

    public void setPorcentComissao(Double porcentComissao) {
        this.porcentComissao = porcentComissao;
    }

    public Boolean getBitStatus() {
        return bitStatus;
    }

    public void setBitStatus(Boolean bitStatus) {
        this.bitStatus = bitStatus;
    }

    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", duracao=" + duracao +
                ", porcentComissao=" + porcentComissao +
                ", bitStatus=" + bitStatus +
                '}';
    }
}

