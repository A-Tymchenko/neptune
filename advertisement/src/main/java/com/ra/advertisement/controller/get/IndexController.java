package com.ra.advertisement.controller.get;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.controller.PathsEnum;
import org.springframework.stereotype.Component;

@Component("favicon.ico")
public class IndexController implements Controller {

    /**
     * This Controller return path to index.jsp.
     *
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to index.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        return PathsEnum.INDEX_PAGE.getPath();
    }
}
