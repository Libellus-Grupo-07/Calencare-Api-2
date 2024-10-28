package com.example.CalencareApi.interfaces;

public interface CrudBasico <Integer, T>{
    T cadastrar(T objeto);
    T buscarTodos();
    T buscarPorId(Integer id);
    T alterar(Integer id, T objeto);
    void deletar(Integer id);
}
