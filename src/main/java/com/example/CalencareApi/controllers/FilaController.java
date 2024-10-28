package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.agendamento.AgendamentoConsultaDto;
import com.example.CalencareApi.listaObj.Fila;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fila")
public class FilaController {

    private Fila<AgendamentoConsultaDto> fila;

    public FilaController() {
        fila = new Fila<>(10); // Capacidade da fila
    }

    @PostMapping("/enqueue")
    public String enqueue(@RequestBody AgendamentoConsultaDto agendamento) {
        try {
            fila.enqueue(agendamento);
            return "Agendamento adicionado à fila: " + agendamento;
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/dequeue")
    public AgendamentoConsultaDto dequeue() {
        try {
            return fila.dequeue();
        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/peek")
    public AgendamentoConsultaDto peek() {
        try {
            return fila.peek();
        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/isEmpty")
    public boolean isEmpty() {
        return fila.isEmpty();
    }

    @GetMapping("/size")
    public int size() {
        return fila.size();
    }

    @GetMapping("/toString")
    public String toStringRepresentation() {
        return fila.toString();
    }
}
