package edu.bbte.idde.bhim2208.dataaccess.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactory.class);
    private static final String DEFAULT_PROFILE = "jdbc";
    private static final String PROFILE_ENV = "APP_PROFILE";
    private static final String CONFIG_FILE_TEMPLATE = "/application-%s.json";

    private static MainConfiguration mainConfiguration = new MainConfiguration();

    static {
        String profile = System.getenv(PROFILE_ENV);
        if (profile == null) {
            profile = System.getProperty("app.profile", DEFAULT_PROFILE);
        }

        String configFile = String.format(CONFIG_FILE_TEMPLATE, profile);
        LOG.info("Using config file: {}", configFile);
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(configFile)) {
            mainConfiguration = objectMapper.readValue(inputStream, MainConfiguration.class);
            LOG.info("Read following configuration: {}", mainConfiguration);
        } catch (IOException e) {
            LOG.error("Error loading configuration", e);
        }
    }

    public static MainConfiguration getMainConfiguration() {
        return mainConfiguration;
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return mainConfiguration.getJdbcConfiguration();
    }

    public static ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return mainConfiguration.getConnectionPoolConfiguration();
    }
}
