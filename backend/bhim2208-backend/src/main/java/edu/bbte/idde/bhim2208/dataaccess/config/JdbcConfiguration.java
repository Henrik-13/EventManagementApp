package edu.bbte.idde.bhim2208.dataaccess.config;

public class JdbcConfiguration {
    private Boolean createTables = false;
    private String driverClass;
    private String url;
    private String user;
    private String password;

    public Boolean isCreateTables() {
        return createTables;
    }

    public void setCreateTables(Boolean createTables) {
        this.createTables = createTables;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JdbcConfiguration{");
        sb.append("createTables=").append(createTables);
        sb.append(", driverClass='").append(driverClass).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
