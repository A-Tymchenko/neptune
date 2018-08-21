package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/plane/delete")
public class DeletePlaneHandler implements ServletHandler {

    private final transient PlaneService planeService;

    @Autowired
    public DeletePlaneHandler(final PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        delete(request, response);
        request.setAttribute("jspPath", "/planes");
    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {
        final String planeId = request.getParameter("id");
        if (Strings.isNotBlank(planeId)) {
            final Plane plane = new Plane();
            plane.setPlaneId(Integer.parseInt(planeId));
            planeService.delete(plane);
        }
    }
}
