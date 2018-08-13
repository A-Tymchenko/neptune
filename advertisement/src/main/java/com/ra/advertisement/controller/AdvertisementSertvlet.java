package com.ra.advertisement.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.config.AdvertisementConfiguration;
import com.ra.advertisement.config.InitDataBase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AdvertisementSertvlet extends HttpServlet {
    private final transient ApplicationContext context = new AnnotationConfigApplicationContext(AdvertisementConfiguration.class);

    @Override
    public void init() {
        final InitDataBase initDataBase = (InitDataBase) context.getBean("initData");
        initDataBase.initData();
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
}
