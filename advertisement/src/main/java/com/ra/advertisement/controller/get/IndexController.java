package com.ra.advertisement.controller.get;

import com.ra.advertisement.controller.Controller;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class IndexController implements Controller {
    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.jsp");
    }
}
