package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.ProviderAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class GetAllProviderController implements Controller {
    private final transient ProviderAdvertisementServiceImpl providerService;

    @Autowired
    public GetAllProviderController(final ProviderAdvertisementServiceImpl providerService) {
        this.providerService = providerService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final List<ProviderDto> listOfProvDto = providerService.getAllEntityService();
        request.setAttribute("providerdto", listOfProvDto);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/allproviders.jsp");
        requestDispatcher.forward(request, response);
    }
}
