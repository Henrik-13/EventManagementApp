package edu.bbte.idde.bhim2208.presentation.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.presentation.error.ErrorMessage;
import edu.bbte.idde.bhim2208.service.EventService;
import edu.bbte.idde.bhim2208.service.EventServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/events")
public class EventServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EventServlet.class);
    EventService eventService = new EventServiceImpl();
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET /events");
        resp.setContentType("application/json");

        if (req.getParameter("id") != null) {
            try {
                Event event = eventService.getEvent(Integer.parseInt(req.getParameter("id")));
                objectMapper.writeValue(resp.getWriter(), event);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), errMess);
            } catch (EventNotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), errMess);
            }
        } else {
            Collection<Event> events = eventService.getEvents();
            objectMapper.writeValue(resp.getOutputStream(), events);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST /events");
        resp.setContentType("application/json");

        try {
            Event event = objectMapper.readValue(req.getInputStream(), Event.class);
            if (event.getTitle() != null
                    && event.getLocation() != null
                    && event.getDate() != null
                    && event.getTime() != null
                    && event.getDescription() != null) {
                eventService.createEvent(event);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), event);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ErrorMessage errMess = new ErrorMessage("Missing required fields",
                        HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), errMess);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), errMess);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("PUT /events");
        resp.setContentType("application/json");

        if (req.getParameter("id") != null) {
            try {
                Integer eventId = Integer.parseInt(req.getParameter("id"));
                Event event = objectMapper.readValue(req.getInputStream(), Event.class);
                if (event.getTitle() != null
                        && event.getLocation() != null
                        && event.getDate() != null
                        && event.getTime() != null
                        && event.getDescription() != null) {
                    event.setId(eventId);
                    eventService.updateEvent(eventId, event);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    objectMapper.writeValue(resp.getWriter(), event);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    ErrorMessage errMess = new ErrorMessage("Missing required fields",
                            HttpServletResponse.SC_BAD_REQUEST);
                    objectMapper.writeValue(resp.getWriter(), errMess);
                }
            } catch (NumberFormatException | IOException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), errMess);
            } catch (EventNotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), errMess);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorMessage errMess = new ErrorMessage("Missing id", HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), errMess);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("DELETE /events");
        resp.setContentType("application/json");

        if (req.getParameter("id") != null) {
            try {
                Integer eventId = Integer.parseInt(req.getParameter("id"));
                eventService.deleteEvent(eventId);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), errMess);
            } catch (EventNotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorMessage errMess = new ErrorMessage(e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), errMess);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorMessage errMess = new ErrorMessage("Missing id", HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), errMess);
        }
    }
}
