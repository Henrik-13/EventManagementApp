package edu.bbte.idde.bhim2208.dataaccess.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainConfiguration {
    @JsonProperty("jdbc")
    private JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();

    @JsonProperty("connectionPoolConfiguration")
    private ConnectionPoolConfiguration connectionPoolConfiguration = new ConnectionPoolConfiguration();

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return connectionPoolConfiguration;
    }

    public void setConnectionPoolConfiguration(ConnectionPoolConfiguration connectionPoolConfiguration) {
        this.connectionPoolConfiguration = connectionPoolConfiguration;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MainConfiguration{");
        sb.append("jdbcConfiguration=").append(jdbcConfiguration);
        sb.append(", connectionPoolConfiguration=").append(connectionPoolConfiguration);
        sb.append('}');
        return sb.toString();
    }
}
