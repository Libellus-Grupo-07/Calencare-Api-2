package com.example.CalencareApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente = "nr.calencare@gmail.com";

    @Async
    public void enviarEmailTexto(String destinatario, String assunto, String texto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(texto);
            javaMailSender.send(message);
            //return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            //  return "Erro ao enviar e-mail: " + e.getLocalizedMessage();
        }
    }
}
