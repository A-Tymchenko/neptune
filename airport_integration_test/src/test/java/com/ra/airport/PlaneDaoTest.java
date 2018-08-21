package com.ra.airport;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.repository.AirPortDao;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.entity.Plane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AirPortConfiguration.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/create_table_skripts.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/tables_backup(data).sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/remove_table_skripts.sql")
})
public class PlaneDaoTest {

    private static final String OWNER = "MAU";
    private static final String MODEL = "Boeing";
    private static final String TYPE = "LargeCarrier";
    private static final Integer PLATE_NUMBER = 13249;

    @Autowired
    private AirPortDao<Plane> planeDao;
    private Plane plane;

    @BeforeEach
    public void beforeTest() throws IOException {
        createPlane();
    }

    private void createPlane()  {
        plane = new Plane();
        plane.setOwner(OWNER);
        plane.setModel(MODEL);
        plane.setType(TYPE);
        plane.setPlateNumber(PLATE_NUMBER);
    }

    @Test
    void whenCreateThenNewPlaneWithIdShouldBeReturned() throws AirPortDaoException {
        Plane createdPlane = planeDao.create(plane);
        assertNotNull(createdPlane);
        Integer planeId = createdPlane.getPlaneId();
        assertNotNull(planeId);
        plane.setPlaneId(planeId);
        assertEquals(plane, createdPlane);
    }

    @Test
    public void whenUpdateThenUpdatedPlaneShouldBeReturned() throws AirPortDaoException {
        Optional<Plane> optionalPlane = planeDao.getById(1);
        assertNotEquals(Optional.empty(), optionalPlane);
        Plane plane = optionalPlane.get();
        Plane expectedPlane = changePlane(plane);
        Plane updatedPlane = planeDao.update(plane);

        assertEquals(expectedPlane, updatedPlane);
    }

    @Test
    public void whenDeleteThenDeleteObjectAndReturnTrue() throws AirPortDaoException {
        Optional<Plane> optionalPlane = planeDao.getById(1);
        assertNotEquals(Optional.empty(), optionalPlane);
        boolean result = planeDao.delete(optionalPlane.get());
        assertTrue(result);
    }

    @Test
    public void whenGetAllThenPlanesFromDBShouldBeReturned() throws AirPortDaoException {
        List<Plane> planes = planeDao.getAll();
        assertTrue(planes.size() == 1);
    }

    private Plane changePlane(Plane plane) {
        plane.setOwner("Lufthansa");
        plane.setModel("Hawker");
        plane.setType("smallcarrier");
        plane.setPlateNumber(4567854);

        return plane;
    }
}
