package com.ra.airport.servlet.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetPlanesHandlerMockitoTest {

    private static final String SPACE = " ";

    private GetPlanesHandler planesHandler;

    private Plane plane;

    private MockHttpServletRequest mockRequest;

    private MockHttpServletResponse mockResponse;

    @Mock
    private PlaneService mockPlaneService;

    @BeforeEach
    public void init() throws AirPortDaoException {
        MockitoAnnotations.initMocks(this);
        plane = createPlane();
        List<Plane> planes = new ArrayList<>(Arrays.asList(plane));

        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        planesHandler = new GetPlanesHandler(mockPlaneService);

        when(mockPlaneService.getAll()).thenReturn(planes);
    }

    @Test
    public void whenGetThenPlaneDTOsShouldBeSetToRequest() throws AirPortDaoException {
        planesHandler.get(mockRequest, mockResponse);
        List<PlaneDto> result = (List<PlaneDto>) mockRequest.getAttribute("planes");

        assertEquals(result.size(), 1);

        PlaneDto planeDto = result.iterator().next();

        assertAll("planeDto",
                  () -> assertEquals(plane.getPlaneId(), planeDto.getPlaneId()),
                  () -> assertEquals(plane.getPlateNumber(), planeDto.getPlateNumber()),
                  () -> assertEquals(plane.getModel(), planeDto.getModel()),
                  () -> assertEquals(plane.getType(), planeDto.getType()),
                  () -> assertEquals(plane.getOwner(), planeDto.getOwner())
        );
    }

    @Test
    public void whenPostThenGetMethodShouldCalledOnce() throws AirPortDaoException {
        planesHandler = mock(GetPlanesHandler.class);
        doCallRealMethod().when(planesHandler).post(mockRequest, mockResponse);
        planesHandler.post(mockRequest, mockResponse);

        verify(planesHandler, times(1)).get(mockRequest, mockResponse);
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
