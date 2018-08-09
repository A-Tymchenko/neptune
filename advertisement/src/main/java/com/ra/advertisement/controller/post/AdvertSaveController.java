package com.ra.advertisement.controller.post;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.AdvertisementAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("saveAdvert")
public class AdvertSaveController implements Controller {
    private final transient AdvertisementAdvertisementServiceImpl advertService;

    @Autowired
    public AdvertSaveController(final AdvertisementAdvertisementServiceImpl advertService) {
        this.advertService = advertService;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<String> answer = advertService.saveEntityService(request);
        request.setAttribute("result", answer);
        final String path = "/advertisementform.jsp";
        return path;
    }
}
