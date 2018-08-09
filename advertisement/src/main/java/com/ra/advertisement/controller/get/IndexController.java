package com.ra.advertisement.controller.get;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import org.springframework.stereotype.Component;

@Component("favicon.ico")
public class IndexController implements Controller {
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final String path = "/index.jsp";
        return path;
    }
}
