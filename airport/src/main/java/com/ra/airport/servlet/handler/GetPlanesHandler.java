package com.ra.airport.servlet.handler;

import java.util.ArrayList;
import java.util.List;
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
public class GetPlanesHandler implements ServletHandler {

    private final transient PlaneService planeService;

    @Autowired
    public GetPlanesHandler(final PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        get(request, response);
    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final List<Plane> planes = planeService.getAll();
        final List<PlaneDto> result = new ArrayList<>();
        for (final Plane plane : planes) {
            result.add(createPlaneDto(plane));
        }
        request.setAttribute("planes", result);
        request.setAttribute("jspPath", "/planes.jsp");
    }

    private PlaneDto createPlaneDto(final Plane plane) {
        final PlaneDto planeDto = new PlaneDto();
        BeanUtils.copyProperties(plane, planeDto);
        return planeDto;
    }
}