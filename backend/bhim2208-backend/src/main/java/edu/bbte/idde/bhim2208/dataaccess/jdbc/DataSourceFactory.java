package edu.bbte.idde.bhim2208.dataaccess.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bhim2208.dataaccess.config.ConfigurationFactory;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            var config = new HikariConfig();
            var jdbcConfig = ConfigurationFactory.getJdbcConfiguration();
            var mainConfig = ConfigurationFactory.getMainConfiguration();
            config.setDriverClassName(jdbcConfig.getDriverClass());
            config.setJdbcUrl(jdbcConfig.getUrl());
            config.setUsername(jdbcConfig.getUser());
            config.setPassword(jdbcConfig.getPassword());
            config.setMaximumPoolSize(mainConfig.getConnectionPoolConfiguration().getPoolSize());
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}
