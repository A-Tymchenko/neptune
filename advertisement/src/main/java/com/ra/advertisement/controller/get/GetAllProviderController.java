package com.ra.advertisement.controller.get;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.controller.PathsEnum;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("allproviders")
public class GetAllProviderController implements Controller {
    private final transient ProviderAdvertisementServiceImpl providerService;

    @Autowired
    public GetAllProviderController(final ProviderAdvertisementServiceImpl providerService) {
        this.providerService = providerService;
    }

    /**
     * This Controller puts into request the list of providerdto objects and return path to allproviders.jsp.
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to allproviders.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<ProviderDto> listOfProvDto = providerService.getAllEntityService();
        request.setAttribute("providerdto", listOfProvDto);
        return PathsEnum.ALL_PROVIDERS.getPath();
    }
}
