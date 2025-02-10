package edu.bbte.idde.bhim2208.dataaccess;

import edu.bbte.idde.bhim2208.dataaccess.exception.DataBaseException;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.BaseEntity;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface Dao<T extends BaseEntity> {
    Integer create(T entity) throws DataBaseException;

    Event getEntity(Integer id) throws EventNotFoundException, DataBaseException;

    void delete(Integer id) throws EventNotFoundException, DataBaseException;

    void update(Integer id, T entity) throws EventNotFoundException, DataBaseException;

    Collection<T> findAll() throws DataBaseException;
}
