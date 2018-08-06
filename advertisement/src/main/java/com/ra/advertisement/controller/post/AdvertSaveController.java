package com.ra.advertisement.controller.post;

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
public class AdvertSaveController implements Controller {
    private static final Logger LOGGER = LogManager.getLogger(AdvertSaveController.class);
    private final transient AdvertisementAdvertisementServiceImpl advertService;

    @Autowired
    public AdvertSaveController(final AdvertisementAdvertisementServiceImpl advertService) {
        this.advertService = advertService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<String> answer = advertService.saveEntityService(request);
        request.setAttribute("result", answer);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/advertisementform.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            final String message = "Trouble in the AdvertSaveController method";
            LOGGER.error(message, e);
        } catch (IOException e) {
            final String message = "Trouble in the AdvertSaveController method";
            LOGGER.error(message, e);
        }
    }
}
