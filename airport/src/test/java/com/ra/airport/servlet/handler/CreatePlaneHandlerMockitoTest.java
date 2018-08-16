package com.ra.airport.servlet.handler;

import java.time.*;
import java.time.format.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CreatePlaneHandlerMockitoTest {

    private static final String SPACE = " ";

    private Plane plane;

    private CreatePlaneHandler createPlaneHandler;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private PlaneService mockPlaneService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        createPlane();
        createPlaneHandler = new CreatePlaneHandler(mockPlaneService);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        createRequest();
    }

    @Test
    public void whenGetThenPathToJspShouldBeSetToRequest() {
        createPlaneHandler.get(mockRequest, mockResponse);
        String result = (String) mockRequest.getAttribute("jspPath");
        assertEquals("/plane/create_plane.jsp", result);
    }

    @Test
    public void whenPostCorrectPlaneShouldBeCreated() throws AirPortDaoException {
        createPlaneHandler.post(mockRequest, mockResponse);
        verify(mockPlaneService, times(1)).create(plane);
    }

    private void createRequest() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.setParameter("model", plane.getModel());
        mockRequest.setParameter("owner", plane.getOwner());
        mockRequest.setParameter("type", plane.getType());
        mockRequest.setParameter("plateNumber", String.valueOf(plane.getPlateNumber()));
    }

    private void createPlane() {
        plane = new Plane();
        plane.setPlaneId(1);
        plane.setPlateNumber(2);
        plane.setModel(SPACE);
        plane.setType(SPACE);
        plane.setOwner(SPACE);
    }
}
