package com.example.CalencareApi.interfaces;

public interface ValidaStatus {

    default String validaStatus(Integer bitStatus) {
        return switch (bitStatus) {
            case 0 -> "Inativo";
            case 1 -> "Ativo";
            case 2 -> "Pendente";
            case 3 -> "Cancelado";
            case 4 -> "Excluído";
            case 5 -> "Finalizado";
            default -> "Status inválido";
        };
    }
}
