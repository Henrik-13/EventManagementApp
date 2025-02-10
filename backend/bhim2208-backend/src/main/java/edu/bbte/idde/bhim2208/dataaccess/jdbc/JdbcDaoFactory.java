package edu.bbte.idde.bhim2208.dataaccess.jdbc;

import edu.bbte.idde.bhim2208.dataaccess.DaoFactory;
import edu.bbte.idde.bhim2208.dataaccess.EventDao;

public class JdbcDaoFactory extends DaoFactory {
    private EventDao eventDao;

    @Override
    public synchronized EventDao getEventDao() {
        if (eventDao == null) {
            eventDao = new EventDaoJdbcImpl();
        }
        return eventDao;
    }

}
