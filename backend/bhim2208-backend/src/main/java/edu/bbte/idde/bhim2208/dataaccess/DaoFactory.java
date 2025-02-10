package edu.bbte.idde.bhim2208.dataaccess;

import edu.bbte.idde.bhim2208.dataaccess.config.ConfigurationFactory;
import edu.bbte.idde.bhim2208.dataaccess.config.JdbcConfiguration;
import edu.bbte.idde.bhim2208.dataaccess.jdbc.JdbcDaoFactory;
import edu.bbte.idde.bhim2208.dataaccess.mem.InMemDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            JdbcConfiguration jdbcConfiguration = ConfigurationFactory.getJdbcConfiguration();
            if (jdbcConfiguration.getDriverClass() == null || jdbcConfiguration.getDriverClass().isEmpty()) {
                instance = new InMemDaoFactory();
            } else {
                instance = new JdbcDaoFactory();
            }
        }
        return instance;
    }

    public abstract EventDao getEventDao();
}
