package com.ra.airport.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.OperationNotSupportedException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.servlet.handler.ServletHandler;
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

    private static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);

    private static AnnotationConfigApplicationContext context;

    /**
     * Init servlet and {@link AnnotationConfigApplicationContext}.
     *
     * @param config servlet config
     */
    @Override
    public void init(final ServletConfig config) {
        context = new AnnotationConfigApplicationContext(AirPortConfiguration.class);
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
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        try {
            this.getHandler(req).get(req, resp);
            redirectRequest(req, resp);
        } catch (OperationNotSupportedException | AirPortDaoException e) {
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
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        try {
            this.getHandler(req).post(req, resp);
            redirectRequest(req, resp);
        } catch (OperationNotSupportedException | AirPortDaoException e) {
            LOGGER.error("Error post request processing", e);
        }
    }

    private void redirectRequest(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String jspPath = (String) req.getAttribute("jspPath");
        if (Strings.isNotBlank(jspPath)) {
            req.getRequestDispatcher(jspPath).forward(req, resp);
        }
    }

    /**
     * Return path to servlet.
     *
     * @param req request
     * @return path to servlet
     */
    private ServletHandler getHandler(final HttpServletRequest req) {
        return (ServletHandler) context.getBean(req.getServletPath());
    }
}