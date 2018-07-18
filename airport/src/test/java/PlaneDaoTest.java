import com.ra.courses.airport.dao.impl.ConnectionFactory;
import com.ra.courses.airport.dao.impl.PlaneDaoException;
import com.ra.courses.airport.dao.impl.PlaneDaoInterface;
import com.ra.courses.airport.entity.Plane;
import com.ra.courses.airport.dao.impl.PlaneDao;


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

    private PlaneDaoInterface<Plane> planeDao;
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
                plane.setPlatenumber(PLATENUMBER);
            }
            
                @Test
    public void whenCreateThenNewFlightWithIdShouldBeReturned() throws PlaneDaoException {
                Plane createdPlane = planeDao.create(plane);
                assertNotNull(createdPlane);
                Integer flightId = createdPlane.getId();
                assertNotNull(flightId);
                plane.setId(flightId);
                assertEquals(plane, createdPlane);
            }
            
            
                @Test
    public void whenUpdateThenUpdatedFlightShouldBeReturned() throws PlaneDaoException {
                Plane createdPlane = planeDao.create(plane);
                Plane expectedPlane = changePlane(createdPlane);
        
                        Plane updatedPlane = planeDao.update(createdPlane);
        
                        assertEquals(expectedPlane, updatedPlane);
            }
            
                @Test
    public void whenDeleteThenDeleteObjectAndReturnTrue() throws PlaneDaoException {
                Plane createdPlane = planeDao.create(plane);
                boolean result = planeDao.delete(createdPlane);
                        assertTrue(result);
            }

                @Test
    public void whenGetAllThenFlightsFromDBShouldBeReturned() throws PlaneDaoException {
                List<Plane> expectedResult = new ArrayList<>();
                expectedResult.add(planeDao.create(plane));
                expectedResult.add(planeDao.create(plane));
        
                        List<Plane> flights = planeDao.getAll();
                        assertEquals(expectedResult, flights);
            }
            
            
            
            
                private Plane changePlane(Plane plane) {
                plane.setOwner("Lufthansa");
                plane.setModel("Hawker");
                plane.setType("smallcarrier");
                plane.setPlatenumber(4567854);
                return plane;
            }
            
            
            
            
            
            



}
