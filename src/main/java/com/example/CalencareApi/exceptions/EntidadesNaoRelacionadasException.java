package com.example.CalencareApi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EntidadesNaoRelacionadasException extends RuntimeException{
    public EntidadesNaoRelacionadasException() {
        super("Entidades não estão relacionadas!");
    }
}
