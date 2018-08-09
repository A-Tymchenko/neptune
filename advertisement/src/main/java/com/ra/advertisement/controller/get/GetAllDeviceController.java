package com.ra.advertisement.controller.get;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.service.DeviceAdvertisementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("alldevices")
public class GetAllDeviceController implements Controller {
    private final transient DeviceAdvertisementServiceImpl deviceService;

    @Autowired
    public GetAllDeviceController(final DeviceAdvertisementServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<DeviceDto> listOfDeviceDto = deviceService.getAllEntityService();
        request.setAttribute("devicedto", listOfDeviceDto);
        final String path = "/WEB-INF/jsp/alldevices.jsp";
        return path;

    }
}

