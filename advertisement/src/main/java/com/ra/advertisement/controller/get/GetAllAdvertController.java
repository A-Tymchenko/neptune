package com.ra.advertisement.controller.get;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.controller.PathsEnum;
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

    /**
     * This Controller puts into request the list of advertDto objects and return path to alladvertisement.jsp.
     *
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to alladvertisement.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<AdvertisementDto> listOfAdvertDto = advertService.getAllEntityService();
        request.setAttribute("advertdto", listOfAdvertDto);
        return PathsEnum.ALL_ADVERTISEMENTS.getPath();
    }
}
