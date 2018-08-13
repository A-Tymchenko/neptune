package com.ra.advertisement.controller.post;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.DeviceAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("saveDevice")
public class DeviceSaveController implements Controller {
    private final transient DeviceAdvertisementServiceImpl deviceService;

    @Autowired
    public DeviceSaveController(final DeviceAdvertisementServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * This Controller puts into request the result of saving of Device and return path to deviceform.jsp.
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to deviceform.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<String> answer = deviceService.saveEntityService(request);
        request.setAttribute("result", answer);
        final String path = "/deviceform.jsp";
        return path;
    }
}
