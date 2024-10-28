package com.example.CalencareApi.controllers;

import com.example.CalencareApi.api.google.calendar.GoogleCalendarService;
import com.example.CalencareApi.entity.Agendamento;
import com.example.CalencareApi.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class GoogleCalendarController {

    //quando a entidade agendamento for ajustada, apenas descomente o código

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    Agendamento agendamento = new Agendamento();


   // @PostMapping("/{idAgendamento}")
   /* public Event createEvent(@PathVariable Integer idAgendamento) {
        Agendamento eventData = agendamentoRepository.findById(idAgendamento).get();

        try {
            return googleCalendarService.createEvent(eventData.getSummary(), eventData.getDescription(), eventData.getStartDate(), eventData.getEndDate(), eventData.getTimeZoneId(), eventData.getCalendarId());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // You should handle exceptions properly here
        }
    }*/
}
