package com.ra.airport.servlet.handler;

import java.util.Optional;
import com.ra.airport.dto.PlaneDto;
import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UpdatePlaneHandlerMockitoTest {

    private static final String SPACE = " ";

    private Plane plane;

    private UpdatePlaneHandler updatePlaneHandler;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private PlaneService mockPlaneService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        createPlane();
        updatePlaneHandler = new UpdatePlaneHandler(mockPlaneService);
        mockResponse = new MockHttpServletResponse();
        createRequest();
        when(mockPlaneService.getById(1)).thenReturn(Optional.of(plane));
    }

    @Test
    public void whenGetThenDtoEntityAndPathToJspShouldBeSetToRequest() throws AirPortDaoException {
        updatePlaneHandler.get(mockRequest, mockResponse);
        String jspPath = (String) mockRequest.getAttribute("jspPath");
        PlaneDto planeDto = (PlaneDto) mockRequest.getAttribute("plane");

        assertEquals("/plane/update_plane.jsp", jspPath);

        assertNotNull(planeDto);
    }

    @Test
    public void whenPostThenPutMethodShouldBeCalled() throws AirPortDaoException {
        updatePlaneHandler = mock(UpdatePlaneHandler.class);
        doCallRealMethod().when(updatePlaneHandler).post(mockRequest, mockResponse);
        updatePlaneHandler.post(mockRequest, mockResponse);

        verify(updatePlaneHandler, times(1)).put(mockRequest, mockResponse);
    }

    @Test
    public void whenPutThenPlaneShouldBeUpdatedAndPathToJspShouldBeSetToRequest() throws AirPortDaoException {
        updatePlaneHandler.put(mockRequest, mockResponse);
        String jspPath = (String) mockRequest.getAttribute("jspPath");

        assertEquals("/planes", jspPath);

        verify(mockPlaneService, times(1)).update(plane);
    }

    @Test
    public void whenGetAndIdNotPassedThenPathToJspShouldBeSetToRequest() throws AirPortDaoException {
        mockRequest.setParameter("id", "");
        updatePlaneHandler.get(mockRequest, mockResponse);
        String jspPath = (String) mockRequest.getAttribute("jspPath");
        PlaneDto plane = (PlaneDto) mockRequest.getAttribute("plane");

        assertEquals("/plane/update_plane.jsp", jspPath);
        assertNull(plane);
    }

    private void createRequest() {
        mockRequest = new MockHttpServletRequest();
        mockRequest.setParameter("id", plane.getPlaneId().toString());
        mockRequest.setParameter("model", plane.getModel());
        mockRequest.setParameter("owner", plane.getOwner());
        mockRequest.setParameter("type", plane.getType());
        mockRequest.setParameter("plateNumber", String.valueOf(plane.getPlateNumber()));
    }

    private Plane createPlane() {
        plane = new Plane();
        plane.setPlaneId(1);
        plane.setPlateNumber(2);
        plane.setModel(SPACE);
        plane.setType(SPACE);
        plane.setOwner(SPACE);
        return plane;
    }
}
