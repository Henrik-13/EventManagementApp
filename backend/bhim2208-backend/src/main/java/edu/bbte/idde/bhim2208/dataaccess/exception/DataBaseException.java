package edu.bbte.idde.bhim2208.dataaccess.exception;

import java.sql.SQLException;

public class DataBaseException extends RuntimeException {
    public DataBaseException() {
        super();
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, SQLException e) {
        super(message, e);
    }
}
