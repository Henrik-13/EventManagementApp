package edu.bbte.idde.bhim2208.dataaccess.mem;

import edu.bbte.idde.bhim2208.dataaccess.EventDao;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventDaoInMemImpl implements EventDao {
    private static final Logger LOG = LoggerFactory.getLogger(EventDaoInMemImpl.class);
    private final Map<Integer, Event> events = new ConcurrentHashMap<>();
    private Integer id = 0;

    @Override
    public Integer create(Event event) {
        event.setId(++id);
        events.put(id, event);
        LOG.info("Event created with id {}", id);
        return id;
    }

    @Override
    public Event getEntity(Integer id) throws EventNotFoundException {
        if (!events.containsKey(id)) {
            LOG.error("Event with id {} not found", id);
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        return events.get(id);
    }

    @Override
    public void delete(Integer id) throws EventNotFoundException {
        if (!events.containsKey(id)) {
            LOG.error("Event with id {} not found ", id);
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        LOG.info("Event with id {} deleted", id);
        events.remove(id);
    }

    @Override
    public void update(Integer id, Event event) throws EventNotFoundException {
        if (!events.containsKey(id)) {
            LOG.error(" Event with id {} not found", id);
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        LOG.info("Event with id {} updated", id);
        event.setId(id);
        events.put(id, event);
    }

    @Override
    public Collection<Event> findAll() {
        return events.values();
    }
}
