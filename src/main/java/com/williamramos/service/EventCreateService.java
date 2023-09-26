package com.williamramos.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.williamramos.facade.ConectCalendarFacade;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class EventCreateService {
    private Event event;
    private DateTime dateTimeStart;
    private DateTime dateTimeEnd;
    private List<EventAttendee> participantes;
    private Calendar service;
    private static final String calendarId = "primary";

    public EventCreateService() {
        this.service = ConectCalendarFacade.getCalendarService();
        this.participantes = new ArrayList<>();
    }

    public Event createEvent(String title, String description) {
        this.event = new Event();
        this.event.setSummary(title);
        this.event.setDescription(description);
        return event;
    }

    public Event start(String data) {
        DateTime startDateTime = new DateTime(data); // Data e hora inicial
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(TimeZone.getTimeZone("America/Araguaina").getID());
        this.event.setStart(start);
        return this.event;
    }

    public Event end(String data) {

        DateTime endDateTime = new DateTime(data); // Data e hora final
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(TimeZone.getTimeZone("America/Araguaina").getID());
        this.event.setEnd(end);
        return this.event;
    }

    public void addParticipantes(String... particpantes) {
        Arrays.stream(particpantes).forEach(participante -> {
            var atendente = new EventAttendee();
            atendente.setEmail(participante);
            this.participantes.add(atendente);
        });

    }

    public Event save() {
        try {
            return service.events().insert(calendarId, this.event).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
