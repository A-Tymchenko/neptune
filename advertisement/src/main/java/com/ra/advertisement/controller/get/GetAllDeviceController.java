package com.ra.advertisement.controller.get;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.advertisement.controller.Controller;
import com.ra.advertisement.controller.PathsEnum;
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

    /**
     * This Controller puts into request the list of devicedto objects and return path to alldevices.jsp.
     *
     * @param request  HttpServlet request
     * @param response HttpServlet response
     * @return path to alldevices.jsp
     */
    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) {
        final List<DeviceDto> listOfDeviceDto = deviceService.getAllEntityService();
        request.setAttribute("devicedto", listOfDeviceDto);
        return PathsEnum.ALL_DEVICES.getPath();
    }
}

