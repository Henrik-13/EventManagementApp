package edu.bbte.idde.bhim2208.dataaccess.config;

public class ConnectionPoolConfiguration {
    private Integer poolSize = 1;

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ConnectionPoolConfiguration{");
        sb.append("poolSize=").append(poolSize);
        sb.append('}');
        return sb.toString();
    }
}
