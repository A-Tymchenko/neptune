package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.AdvertisementAdvertisementServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class GetAllAdvertController implements Controller {
    private static final Logger LOGGER = LogManager.getLogger(GetAllAdvertController.class);
    private final transient AdvertisementAdvertisementServiceImpl advertService;

    @Autowired
    public GetAllAdvertController(final AdvertisementAdvertisementServiceImpl advertService) {
        this.advertService = advertService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<AdvertisementDto> listOfAdvertDto = advertService.getAllEntityService();
        request.setAttribute("advertdto", listOfAdvertDto);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/alladvertisement.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            final String message = "Trouble in the AdvertController method";
            LOGGER.error(message, e);
        } catch (IOException e) {
            final String message = "Trouble in the AdvertController method";
            LOGGER.error(message, e);
        }
    }
}
