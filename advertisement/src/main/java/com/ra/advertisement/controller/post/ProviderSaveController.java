package com.ra.advertisement.controller.post;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
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
public class ProviderSaveController implements Controller {
    private static final Logger LOGGER = LogManager.getLogger(ProviderSaveController.class);
    private final transient ProviderAdvertisementServiceImpl providerService;

    @Autowired
    public ProviderSaveController(final ProviderAdvertisementServiceImpl providerService) {
        this.providerService = providerService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<String> answer = providerService.saveEntityService(request);
        request.setAttribute("result", answer);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/providerform.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            final String message = "Trouble in the ProviderSaveController method";
            LOGGER.error(message, e);
        } catch (IOException e) {
            final String message = "Trouble in the ProviderSaveController method";
            LOGGER.error(message, e);
        }
    }
}
