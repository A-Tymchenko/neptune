package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.dto.PlaneDto;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePlaneHandler implements ServletHandler {

    private final transient PlaneService planeService;

    @Autowired
    public CreatePlaneHandler(final PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final PlaneDto planeDto = createPlaneDto(request);
        final Plane plane = new Plane();
        BeanUtils.copyProperties(planeDto, plane);
        planeService.create(plane);

        request.setAttribute("jspPath", "/planes");
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("jspPath", "/plane/create_plane.jsp");
    }

    private PlaneDto createPlaneDto(final HttpServletRequest request) {
        final PlaneDto planeDto = new PlaneDto();
        planeDto.setModel(request.getParameter("model"));
        planeDto.setOwner(request.getParameter("owner"));
        planeDto.setType(request.getParameter("type"));
        planeDto.setPlateNumber(Integer.valueOf(request.getParameter("plateNumber")));

        return planeDto;
    }
}