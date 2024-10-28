package com.example.CalencareApi.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Mensagem {
    private Integer id;
    private String descricao;
    private LocalTime horario;
    private LocalDate data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", horario=" + horario +
                ", data=" + data +
                '}';
    }
}
