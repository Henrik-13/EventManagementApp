package edu.bbte.idde.bhim2208.dataaccess.jdbc;

import edu.bbte.idde.bhim2208.dataaccess.EventDao;
import edu.bbte.idde.bhim2208.dataaccess.exception.DataBaseException;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class EventDaoJdbcImpl implements EventDao {
    private static final Logger LOG = LoggerFactory.getLogger(EventDaoJdbcImpl.class);
    private final DataSource dataSource;

    public EventDaoJdbcImpl() {
        dataSource = DataSourceFactory.getDataSource();
    }

    @Override
    public Integer create(Event entity) {
        String query = "INSERT INTO events (title, location, date, time, description, online) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getLocation());
            stmt.setDate(3, Date.valueOf(entity.getDate()));
            stmt.setTime(4, Time.valueOf(entity.getTime()));
            stmt.setString(5, entity.getDescription());
            stmt.setBoolean(6, entity.isOnline());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                        LOG.info("Created event with id {}", entity.getId());
                    }
                }
            }
            return entity.getId();
        } catch (SQLException e) {
            LOG.error("Error creating event", e);
            throw new DataBaseException("Error creating event", e);
        }
    }

    @Override
    public Event getEntity(Integer id) throws EventNotFoundException {
        String query = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getEvent(rs);
                }
                LOG.warn("No event found with id {}", id);
                throw new EventNotFoundException("No event found with id " + id);
            }
        } catch (SQLException e) {
            LOG.error("Error getting event", e);
            throw new DataBaseException("Error getting event", e);
        }
    }

    @Override
    public void delete(Integer id) throws EventNotFoundException {
        String query = "DELETE FROM events WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                LOG.info("Deleted event with id {}", id);
            } else {
                LOG.warn("No event found while deleting with id {}", id);
                throw new EventNotFoundException("No event found while deleting with id " + id);
            }
        } catch (SQLException e) {
            LOG.error("Error deleting event", e);
            throw new DataBaseException("Error deleting event", e);
        }
    }

    @Override
    public void update(Integer id, Event entity) throws EventNotFoundException {
        String query = "UPDATE events SET "
                + "title = ?, location = ?, date = ?, time = ?, description = ?, online = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getLocation());
            stmt.setDate(3, Date.valueOf(entity.getDate()));
            stmt.setTime(4, Time.valueOf(entity.getTime()));
            stmt.setString(5, entity.getDescription());
            stmt.setBoolean(6, entity.isOnline());
            stmt.setInt(7, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                LOG.info("Updated event with id {}", id);
            } else {
                LOG.warn("No event found while updating with id {}", id);
                throw new EventNotFoundException("No event found while updating with id " + id);
            }
        } catch (SQLException e) {
            LOG.error("Error updating event", e);
            throw new DataBaseException("Error updating event", e);
        }
    }

    @Override
    public Collection<Event> findAll() {
        String query = "SELECT * FROM events";
        Collection<Event> events = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(getEvent(rs));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error finding events", e);
            throw new DataBaseException("Error finding events", e);
        }
        return events;
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setLocation(rs.getString("location"));
        event.setDate(rs.getDate("date").toLocalDate());
        event.setTime(rs.getTime("time").toLocalTime());
        event.setDescription(rs.getString("description"));
        event.setOnline(rs.getBoolean("online"));
        return event;
    }
}
