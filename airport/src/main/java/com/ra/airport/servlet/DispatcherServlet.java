package com.ra.airport.servlet;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.RunScript;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private static HandlerFactory handlerFactory;

    private static final Logger LOGGER = LogManager.getLogger(FlightDao.class);

    @Override
    public void init(ServletConfig config) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AirPortConfiguration.class);
        handlerFactory = context.getBean(HandlerFactory.class);
        initDataBase(context);
    }

    private void initDataBase(ApplicationContext context) {
        try {
            Connection connection = context.getBean(DataSource.class).getConnection();
            Resource metaDataResource = new ClassPathResource("sql/create_table_skripts.sql");
            Resource dataResource = new ClassPathResource("sql/tables_backup(data).sql");
            RunScript.execute(connection, new InputStreamReader(metaDataResource.getInputStream()));
            RunScript.execute(connection, new InputStreamReader(dataResource.getInputStream()));
        } catch (SQLException | IOException e) {
            LOGGER.error("Failed init data base ", e);
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            handlerFactory.handleGetRequest(this.getPath(req), req, resp);
        } catch (AirPortDaoException e) {

        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            handlerFactory.handlePostRequest(this.getPath(req), req, resp);
        } catch (AirPortDaoException e) {

        }
    }

    private String getPath(final HttpServletRequest req) {
        return req.getServletPath();
    }
}
