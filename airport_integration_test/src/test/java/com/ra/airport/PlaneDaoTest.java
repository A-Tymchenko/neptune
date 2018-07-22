package com.ra.airport;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.entity.Plane;
import com.ra.airport.dao.impl.PlaneDao;


import com.ra.airport.factory.ConnectionFactory;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaneDaoTest {

    private static final String OWNER = "MAU";
    private static final String MODEL = "Boeing";
    private static final String TYPE = "LargeCarrier";
    private static final Integer PLATENUMBER = 132498789;

    private AirPortDao<Plane> planeDao;
    private Plane plane;

    @BeforeEach
    public void beforeTest() throws SQLException, IOException {
        createDataBaseTable();
        createPlane();
    }

    @AfterEach
    public void afterTest() throws SQLException, IOException {
        deleteTable();
    }

    private void createDataBaseTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader("src/test/resources/sql/plane_table_backup.sql"));
    }

    private void deleteTable() throws SQLException, IOException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        RunScript.execute(connection, new FileReader("src/test/resources/sql/remove_plane_table.sql"));
    }


    private void createPlane() throws IOException {

        planeDao = new PlaneDao(ConnectionFactory.getInstance());
        plane = new Plane();
        plane.setOwner(OWNER);
        plane.setModel(MODEL);
        plane.setType(TYPE);
        plane.setPlateNumber(PLATENUMBER);
    }

    @Test
    public void whenCreateThenNewPlaneWithIdShouldBeReturned() throws AirPortDaoException {
        Plane createdPlane = planeDao.create(plane);
        assertNotNull(createdPlane);
        Integer planeId = createdPlane.getId();
        assertNotNull(planeId);
        plane.setId(planeId);
        assertEquals(plane, createdPlane);
    }


    @Test
    public void whenUpdateThenUpdatedPlaneShouldBeReturned() throws AirPortDaoException {
        Plane createdPlane = planeDao.create(plane);
        Plane expectedPlane = changePlane(createdPlane);
        Plane updatedPlane = planeDao.update(createdPlane);

        assertEquals(expectedPlane, updatedPlane);
    }

    @Test
    public void whenDeleteThenDeleteObjectAndReturnTrue() throws AirPortDaoException {
        Plane createdPlane = planeDao.create(plane);
        boolean result = planeDao.delete(createdPlane);

        assertTrue(result);
    }

    @Test
    public void whenGetAllThenPlanesFromDBShouldBeReturned() throws AirPortDaoException {
        List<Plane> expectedResult = new ArrayList<>();
        expectedResult.add(planeDao.create(plane));
        expectedResult.add(planeDao.create(plane));

        List<Plane> planes = planeDao.getAll();
        assertEquals(expectedResult, planes);
    }

    private Plane changePlane(Plane plane) {
        plane.setOwner("Lufthansa");
        plane.setModel("Hawker");
        plane.setType("smallcarrier");
        plane.setPlateNumber(4567854);

        return plane;
    }
}
