package com.ra.airport.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.handler.ServletHandler;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.h2.tools.RunScript;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Main application servlet. Redirecting all users requests to particular {@link ServletHandler}
 */
@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private static HandlerFactory handlerFactory;

    private static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);

    /**
     * Init servlet and {@link AnnotationConfigApplicationContext}.
     * For {@link HandlerFactory} class initialization.
     *
     * @param config servlet config
     */
    @Override
    public void init(final ServletConfig config) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AirPortConfiguration.class);
        handlerFactory = context.getBean(HandlerFactory.class);
        initDataBase(context);
    }

    /**
     * Create DB with all necessary data.
     * @param context {@link ApplicationContext} instance
     */
    private void initDataBase(final ApplicationContext context) {
        try {
            final Connection connection = context.getBean(DataSource.class).getConnection();
            final Resource metaDataResource = new ClassPathResource("sql/create_table_skripts.sql");
            final Resource dataResource = new ClassPathResource("sql/tables_backup(data).sql");
            RunScript.execute(connection, new InputStreamReader(metaDataResource.getInputStream()));
            RunScript.execute(connection, new InputStreamReader(dataResource.getInputStream()));
        } catch (SQLException | IOException e) {
            LOGGER.error("Failed init data base ", e);
        }
    }

    /**
     * Process get request.
     *
     * @param req request
     * @param resp response
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        try {
            handlerFactory.handleGetRequest(this.getPath(req), req, resp);
            String jspPath = (String) req.getAttribute("jspPath");
            if (Strings.isNotBlank(jspPath)) {
               req.getRequestDispatcher(jspPath).forward(req, resp);
            }
        } catch (AirPortDaoException e) {
            LOGGER.error("Error get request processing", e);
        }
    }

    /**
     * Process post request.
     *
     * @param req request
     * @param resp response
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            handlerFactory.handlePostRequest(this.getPath(req), req, resp);
        } catch (AirPortDaoException e) {
            LOGGER.error("Error post request processing", e);
        }
    }

    /**
     * Return path to servlet.
     *
     * @param req request
     * @return path to servlet
     */
    private String getPath(final HttpServletRequest req) {
        return req.getServletPath();
    }
}
