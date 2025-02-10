package edu.bbte.idde.bhim2208.dataaccess;


import edu.bbte.idde.bhim2208.dataaccess.exception.DataBaseException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.dataaccess.model.Participant;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EventDao extends Dao<Event> {
    Collection<Event> findByTitle(String title) throws DataBaseException;

    List<Participant> findAllParticipants(Event event);
}
