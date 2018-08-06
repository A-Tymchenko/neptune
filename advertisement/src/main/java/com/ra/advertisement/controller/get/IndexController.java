package com.ra.advertisement.controller.get;

import com.ra.advertisement.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class IndexController implements Controller {
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            response.sendRedirect("/index.jsp");
        } catch (IOException e) {
            final String message = "Trouble in the IndexController method";
            LOGGER.error(message, e);
        }

    }
}
