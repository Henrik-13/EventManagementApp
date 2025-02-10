package edu.bbte.idde.bhim2208.service;

import edu.bbte.idde.bhim2208.dataaccess.EventDao;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Lazy
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Override
    public Integer createEvent(Event event) {
        return eventDao.create(event);
    }

    @Override
    public Event getEvent(Integer id) throws EventNotFoundException {
        return eventDao.getEntity(id);
    }

    @Override
    public void deleteEvent(Integer id) throws EventNotFoundException {
        eventDao.delete(id);
    }

    @Override
    public void updateEvent(Integer id, Event event) throws EventNotFoundException {
        eventDao.update(id, event);
    }

    @Override
    public Collection<Event> getEvents() {
        return eventDao.findAll();
    }

    @Override
    public Collection<Event> findByTitle(String title) {
        return eventDao.findByTitle(title);
    }

    @Override
    public List<Participant> findParticipantsByEvent(Event event) {
        return eventDao.findAllParticipants(event);
    }
}
