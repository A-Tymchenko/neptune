package com.ra.advertisement.controller.post;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.PublisherAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("savePublisher")
public class PublisherSaveController implements Controller {
    private final transient PublisherAdvertisementServiceImpl publisherService;

    @Autowired
    public PublisherSaveController(final PublisherAdvertisementServiceImpl publisherService) {
        this.publisherService = publisherService;
    }

    /**
     * This Controller puts into request the result of saving of Publisher and return path to publisherform.jsp.
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to publisherform.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<String> answer = publisherService.saveEntityService(request);
        request.setAttribute("result", answer);
        final String path = "/publisherform.jsp";
        return path;
    }
}