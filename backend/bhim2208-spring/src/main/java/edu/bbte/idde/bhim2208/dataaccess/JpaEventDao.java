package edu.bbte.idde.bhim2208.dataaccess;

import edu.bbte.idde.bhim2208.dataaccess.exception.DataBaseException;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@Profile("jpa")
public interface JpaEventDao extends EventDao, JpaRepository<Event, Integer> {

    @Override
    default Integer create(Event entity) throws DataBaseException {
        return save(entity).getId();
    }

    @Override
    default Event getEntity(Integer id) throws EventNotFoundException, DataBaseException {
        return findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
    }

    @Override
    default void delete(Integer id) throws EventNotFoundException, DataBaseException {
        if (findById(id).isPresent()) {
            deleteById(id);
        } else {
            throw new EventNotFoundException("Event not found with id " + id);
        }
    }

    @Override
    default void update(Integer id, Event entity) throws EventNotFoundException, DataBaseException {
        if (findById(id).isEmpty()) {
            throw new EventNotFoundException();
        } else {
            entity.setId(id);
            save(entity);
        }
    }

    @Override
    default Collection<Event> findByTitle(String title) throws DataBaseException {
        return findByTitleContaining(title);
    }

    @Override
    default List<Participant> findAllParticipants(Event event) throws DataBaseException {
        return event.getParticipants();
    }

    Collection<Event> findByTitleContaining(String title);
}
