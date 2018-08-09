package com.ra.advertisement.controller.get;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.service.AdvertisementAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("alladvertisement")
public class GetAllAdvertController implements Controller {
    private final transient AdvertisementAdvertisementServiceImpl advertService;

    @Autowired
    public GetAllAdvertController(final AdvertisementAdvertisementServiceImpl advertService) {
        this.advertService = advertService;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<AdvertisementDto> listOfAdvertDto = advertService.getAllEntityService();
        request.setAttribute("advertdto", listOfAdvertDto);
        final String path = "/WEB-INF/jsp/alladvertisement.jsp";
        return path;
    }
}
