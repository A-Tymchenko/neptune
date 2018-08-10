package com.ra.advertisement.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.ra.advertisement.config.AdvertisementConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AdvertisementSertvlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AdvertisementSertvlet.class);
    private final transient ApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);

    @Override
    public void init() {
        ApplicationContext contextForDb = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);
        initDataBase(contextForDb);
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String[] resultOfSplit = req.getRequestURI().split("/");
        final String key = resultOfSplit[resultOfSplit.length - 1];
        final Controller controller = (Controller) context.getBean(key);
        final String path = controller.execute(req, resp);
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String reqestParameter = req.getParameter("saveEntity");
        final Controller controller = (Controller) context.getBean(reqestParameter);
        final String path = controller.execute(req, resp);
        final RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    /**
     * This method run script to create tables in DB
     *
     * @param context ApplicationContext.
     */
    public void initDataBase(final ApplicationContext context) {
        try {
            final Connection connection = context.getBean(DataSource.class).getConnection();
            final Resource creationTables = new ClassPathResource("./advertisement_db.sql");
            RunScript.execute(connection, new InputStreamReader(creationTables.getInputStream()));
        } catch (SQLException e) {
            final String message = "Trouble in the initDataBase method";
            LOGGER.error(message, e);
        } catch (FileNotFoundException e) {
            final String message = "Trouble in the initDataBase method";
            LOGGER.error(message, e);
        } catch (IOException e) {
            final String message = "Trouble in the initDataBase method";
            LOGGER.error(message, e);
        }
    }
}
