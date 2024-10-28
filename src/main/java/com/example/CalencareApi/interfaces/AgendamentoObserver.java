package com.example.CalencareApi.interfaces;

import com.example.CalencareApi.entity.Agendamento;
import com.example.CalencareApi.service.EmailService;

public interface AgendamentoObserver {
    void notificar(EmailService emailService, Agendamento agendamento);
}
