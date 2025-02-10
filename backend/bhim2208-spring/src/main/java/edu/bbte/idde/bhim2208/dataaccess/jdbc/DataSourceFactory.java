package edu.bbte.idde.bhim2208.dataaccess.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("jdbc")
public class DataSourceFactory {

    @Autowired
    private JdbcConfiguration config;

    @Bean
    @Lazy
    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(config.getJdbcDriverClassName());
        dataSource.setJdbcUrl(config.getJdbcUrl());
        dataSource.setUsername(config.getJdbcUsername());
        dataSource.setPassword(config.getJdbcPassword());
        dataSource.setMaximumPoolSize(Integer.parseInt(config.getMaxPoolSize()));
        return dataSource;
    }
}
