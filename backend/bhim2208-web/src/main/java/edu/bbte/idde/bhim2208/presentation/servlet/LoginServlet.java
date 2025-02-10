package edu.bbte.idde.bhim2208.presentation.servlet;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ThymeleafEngineFactory.buildEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("GET /login");
        Map<String, Object> model = new ConcurrentHashMap<>();
        if (req.getAttribute("error") != null) {
            model.put("error", req.getAttribute("error"));
        }

        ThymeleafEngineFactory.process(req, resp, "login", model);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("POST /login");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            req.getSession().setAttribute("username", username);
            resp.sendRedirect("index");
        } else {
            req.setAttribute("error", "Incorrect username or password");
            doGet(req, resp);
        }
    }
}
