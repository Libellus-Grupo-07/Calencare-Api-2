package com.example.CalencareApi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ServicoPrecoNomeJaExisteException extends RuntimeException {
    public ServicoPrecoNomeJaExisteException() {
        super("Já existe um serviço com o nome informado");
    }
}
