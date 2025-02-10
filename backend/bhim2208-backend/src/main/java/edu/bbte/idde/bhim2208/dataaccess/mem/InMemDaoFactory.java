package edu.bbte.idde.bhim2208.dataaccess.mem;

import edu.bbte.idde.bhim2208.dataaccess.DaoFactory;
import edu.bbte.idde.bhim2208.dataaccess.EventDao;

public class InMemDaoFactory extends DaoFactory {
    private EventDaoInMemImpl eventDao;

    @Override
    public synchronized EventDao getEventDao() {
        if (eventDao == null) {
            eventDao = new EventDaoInMemImpl();
        }
        return eventDao;
    }
}
