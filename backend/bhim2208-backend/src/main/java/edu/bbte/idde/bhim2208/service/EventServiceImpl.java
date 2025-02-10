package edu.bbte.idde.bhim2208.service;

import edu.bbte.idde.bhim2208.dataaccess.DaoFactory;
import edu.bbte.idde.bhim2208.dataaccess.EventDao;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;

import java.util.Collection;

public class EventServiceImpl implements EventService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private final EventDao eventDao = daoFactory.getEventDao();

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
}
