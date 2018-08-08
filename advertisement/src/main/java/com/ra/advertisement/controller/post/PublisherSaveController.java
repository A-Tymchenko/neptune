package com.ra.advertisement.controller.post;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.PublisherAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class PublisherSaveController implements Controller {
    private final transient PublisherAdvertisementServiceImpl publisherService;

    @Autowired
    public PublisherSaveController(final PublisherAdvertisementServiceImpl publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final List<String> answer = publisherService.saveEntityService(request);
        request.setAttribute("result", answer);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/publisherform.jsp");
        requestDispatcher.forward(request, response);
    }
}