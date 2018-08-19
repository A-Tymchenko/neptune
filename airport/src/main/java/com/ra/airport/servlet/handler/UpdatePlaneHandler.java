package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.dto.PlaneDto;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePlaneHandler implements ServletHandler {

    private final transient PlaneService planeService;

    @Autowired
    public UpdatePlaneHandler(final PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        put(request, response);
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final String planeId = request.getParameter("id");
        if (Strings.isNotBlank(planeId)) {
            planeService.getById(Integer.parseInt(planeId)).ifPresent(
                    plane -> {
                        final PlaneDto planeDto = new PlaneDto();
                        BeanUtils.copyProperties(plane, planeDto);
                        request.setAttribute("plane", planeDto);
                    });
        }
        request.setAttribute("jspPath", "/plane/update_plane.jsp");
    }

    @Override
    public void put(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final PlaneDto planeDto = createPlaneDto(request);
        final Plane plane = new Plane();
        BeanUtils.copyProperties(planeDto, plane);
        planeService.update(plane);

        request.setAttribute("jspPath", "/planes");
    }

    private PlaneDto createPlaneDto(final HttpServletRequest request) {
        final PlaneDto planeDto = new PlaneDto();
        planeDto.setPlaneId(Integer.parseInt(request.getParameter("id")));
        planeDto.setModel(request.getParameter("model"));
        planeDto.setOwner(request.getParameter("owner"));
        planeDto.setType(request.getParameter("type"));
        planeDto.setPlateNumber(Integer.valueOf(request.getParameter("plateNumber")));

        return planeDto;
    }
}
