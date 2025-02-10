package edu.bbte.idde.bhim2208.service;

import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;

import java.util.Collection;

public interface EventService {
    Integer createEvent(Event event);

    Event getEvent(Integer id) throws EventNotFoundException;

    void deleteEvent(Integer id) throws EventNotFoundException;

    void updateEvent(Integer id, Event event) throws EventNotFoundException;

    Collection<Event> getEvents();
}
