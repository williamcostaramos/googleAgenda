package com.williamramos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.williamramos.facade.ConectCalendarFacade;

public class ListEventsService {
    private List<Event> eventos;
    private Calendar calendar;

    public ListEventsService() {
        this.calendar = ConectCalendarFacade.getCalendarService();
        this.eventos = new ArrayList<>();
    }

    public List<Event> list() {
        try {
            DateTime dataDeInicio = new DateTime(System.currentTimeMillis() - 360 * 24 * 60 * 60 * 1000); 
            Events events = this.calendar.events().list("primary")
            .setMaxResults(100)
            .setSingleEvents(false)
            .setTimeMin(dataDeInicio)
            .execute();
            this.eventos = events.getItems();
            return this.eventos;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public List<Event> list(String calendarId, Integer maxResult, boolean singleEvents) {
        try {
            Events events = this.calendar.events().list(calendarId)
            .setMaxResults(maxResult)
            .setSingleEvents(singleEvents)
            .execute();
            this.eventos = events.getItems();
            return this.eventos;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
