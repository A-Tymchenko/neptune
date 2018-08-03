package com.ra.airport.servlet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import com.ra.airport.servlet.handler.factory.HandlerFactory;
import org.h2.tools.RunScript;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {

    private static HandlerFactory handlerFactory;

    @Override
    public void init(ServletConfig config) {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AirPortConfiguration.class);
        handlerFactory = context.getBean(HandlerFactory.class);

        initDataBase(context);
    }

    private void initDataBase(ApplicationContext context) {
        try {
            Connection connection = context.getBean(DataSource.class).getConnection();
            RunScript.execute(connection, new FileReader("src/resources/sql/create_table_scripts.sql"));
            RunScript.execute(connection, new FileReader("src/resources/sql/tables_backup(data).sql"));
        } catch (SQLException | FileNotFoundException e) {
           //todo log exception
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        try {
            handlerFactory.handleGetRequest(this.getPath(req), req, resp);
        } catch (AirPortDaoException e) {

        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        handlerFactory.handlePostRequest(this.getPath(req), req, resp);
    }

    private String getPath(final HttpServletRequest req) {
        return req.getServletPath();
    }
}
