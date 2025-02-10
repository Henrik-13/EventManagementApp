package edu.bbte.idde.bhim2208.presentation.servlet;

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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/index")
public class ThymeleafServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ThymeleafServlet.class);
    EventService eventService = new EventServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ThymeleafEngineFactory.buildEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Request arrived to thymeleaf servlet");
        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("events", eventService.getEvents());

        ThymeleafEngineFactory.process(req, resp, "index", model);
    }
}
