package com.ra.airport.servlet.handler;

import com.ra.airport.entity.Plane;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.service.PlaneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeletePlaneMockitoTest {

    private Plane plane;

    private DeletePlaneHandler deletePlaneHandler;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private PlaneService mockPlaneService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        plane = new Plane();
        plane.setPlaneId(1);
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        deletePlaneHandler = mock(DeletePlaneHandler.class);
        doCallRealMethod().when(deletePlaneHandler).post(mockRequest, mockResponse);
        doCallRealMethod().when(deletePlaneHandler).delete(mockRequest, mockResponse);
    }

    @Test
    public void whenDeleteThenCorrectEntityShouldBePassToServiceMethod() throws AirPortDaoException {
        deletePlaneHandler = new DeletePlaneHandler(mockPlaneService);
        mockRequest.setParameter("id", "1");
        deletePlaneHandler.delete(mockRequest, mockResponse);
        verify(mockPlaneService, times(1)).delete(plane);
    }

    @Test
    public void whenPostThenDeleteShouldBeExecutedAndPathToPlanesTableShouldBeSet() throws AirPortDaoException {
        deletePlaneHandler.post(mockRequest, mockResponse);
        verify(deletePlaneHandler, times(1)).delete(mockRequest, mockResponse);
        String result = (String) mockRequest.getAttribute("jspPath");

        assertEquals("/planes", result);
    }

    @Test
    public void whenDeleteAndIdNotPassedThenServiceShouldNotBeCalled() throws AirPortDaoException {
        mockRequest.setParameter("id", "");
        deletePlaneHandler.delete(mockRequest, mockResponse);

        verify(mockPlaneService, times(0)).delete(plane);
    }
}
