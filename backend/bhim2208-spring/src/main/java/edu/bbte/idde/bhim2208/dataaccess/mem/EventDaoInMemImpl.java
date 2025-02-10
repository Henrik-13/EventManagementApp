package edu.bbte.idde.bhim2208.dataaccess.mem;

import edu.bbte.idde.bhim2208.dataaccess.EventDao;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Lazy
@Profile("memory")
@Slf4j
public class EventDaoInMemImpl implements EventDao {
    private final Map<Integer, Event> events = new ConcurrentHashMap<>();
    private Integer id = 0;

    @Override
    public Integer create(Event event) {
        event.setId(++id);
        events.put(id, event);
        log.info("Event created with id {}", id);
        return id;
    }

    @Override
    public Event getEntity(Integer id) throws EventNotFoundException {
        if (!events.containsKey(id)) {
            log.error("Event with id {} not found", id);
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        return events.get(id);
    }

    @Override
    public void delete(Integer id) throws EventNotFoundException {
        if (!events.containsKey(id)) {
            log.error("Event with id {} not found ", id);
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        log.info("Event with id {} deleted", id);
        events.remove(id);
    }

    @Override
    public void update(Integer id, Event event) throws EventNotFoundException {
        if (!events.containsKey(id)) {
            log.error(" Event with id {} not found", id);
            throw new EventNotFoundException("Event with id " + id + " not found");
        }
        log.info("Event with id {} updated", id);
        event.setId(id);
        events.put(id, event);
    }

    @Override
    public Collection<Event> findAll() {
        return events.values();
    }

    @Override
    public Collection<Event> findByTitle(String title) {
        return events.values()
                .stream()
                .filter(event -> event.getTitle().startsWith(title))
                .toList();
    }

    @Override
    public List<Participant> findAllParticipants(Event event) {
        return new ArrayList<>();
    }
}
