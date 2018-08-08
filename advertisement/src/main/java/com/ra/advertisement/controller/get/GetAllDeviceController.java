package com.ra.advertisement.controller.get;

import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.service.DeviceAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class GetAllDeviceController implements Controller {
    private transient DeviceAdvertisementServiceImpl deviceService;

    @Autowired
    public GetAllDeviceController(final DeviceAdvertisementServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final List<DeviceDto> listOfDeviceDto = deviceService.getAllEntityService();
        request.setAttribute("devicedto", listOfDeviceDto);
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/alldevices.jsp");
        requestDispatcher.forward(request, response);
    }
}

