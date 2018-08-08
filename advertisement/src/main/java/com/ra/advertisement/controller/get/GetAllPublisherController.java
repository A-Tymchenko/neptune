package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.PublisherDto;
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
public class GetAllPublisherController implements Controller {
    private final transient PublisherAdvertisementServiceImpl publisherService;

    @Autowired
    public GetAllPublisherController(final PublisherAdvertisementServiceImpl publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final List<PublisherDto> listOfPublDto = publisherService.getAllEntityService();
        request.setAttribute("publisherdto", listOfPublDto);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/allpublishers.jsp");
        requestDispatcher.forward(request, response);
    }
}