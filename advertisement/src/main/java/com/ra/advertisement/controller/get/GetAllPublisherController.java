package com.ra.advertisement.controller.get;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.controller.PathsEnum;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.service.PublisherAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("allpublishers")
public class GetAllPublisherController implements Controller {
    private final transient PublisherAdvertisementServiceImpl publisherService;

    @Autowired
    public GetAllPublisherController(final PublisherAdvertisementServiceImpl publisherService) {
        this.publisherService = publisherService;
    }

    /**
     * This Controller puts into request the list of publisherdto objects and return path to allpublishers.jsp.
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to allpublishers.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<PublisherDto> listOfPublDto = publisherService.getAllEntityService();
        request.setAttribute("publisherdto", listOfPublDto);
        return PathsEnum.ALL_PUBLISHERS.getPath();
    }
}