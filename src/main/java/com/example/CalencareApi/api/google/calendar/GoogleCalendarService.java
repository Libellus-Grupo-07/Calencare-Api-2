package com.example.CalencareApi.api.google.calendar;

import com.example.CalencareApi.entity.Agendamento;
import com.example.CalencareApi.repository.AgendamentoRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.services.calendar.model.Event;
import org.springframework.web.bind.annotation.PathVariable;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

@Service
public class GoogleCalendarService {
    @Autowired
    AgendamentoRepository agendamentoRepository;

    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/client.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    public Event createEvent(String summary, String description, Date startDate, Date endDate, String timeZoneId, String calendarId) throws Exception {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();

        com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event()
                .setSummary(summary)
                .setDescription(description);

        TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        EventDateTime start = new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDate, timeZone));
        event.setStart(start);
        EventDateTime end = new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDate, timeZone));
        event.setEnd(end);

        return service.events().insert(calendarId, event).execute();
    }

    private com.google.api.client.auth.oauth2.Credential getCredentials(final HttpTransport HTTP_TRANSPORT) throws Exception {
        InputStream in = GoogleCalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singletonList(CalendarScopes.CALENDAR))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(flow, new com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver()).authorize("user");
    }

    public Event createEvent(@PathVariable Integer idAgendamento) {
        Agendamento eventData = agendamentoRepository.findById(idAgendamento).get();

        try {
            return createEvent(eventData.getSummary(), eventData.getDescription(), eventData.getStartDate(), eventData.getEndDate(), eventData.getTimeZoneId(), eventData.getCalendarId());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // You should handle exceptions properly here
        }
    }
}