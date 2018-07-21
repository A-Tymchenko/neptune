import com.ra.courses.airport.dao.impl.ConnectionFactory;
import com.ra.courses.airport.dao.impl.PlaneDaoException;
import com.ra.courses.airport.dao.impl.PlaneDaoInterface;
import com.ra.courses.airport.entity.Plane;
import com.ra.courses.airport.dao.impl.PlaneDao;
import static com.ra.courses.airport.dao.impl.ExceptionMessage.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PlaneDaoMockitoTest {


    private static final String INSERT_PLANE_SQL = "INSERT INTO plane "
            +"(owner, type, model, platenumber) "
            +" VALUES(?,?,?,?)";
    private static final String UPDATE_PLANE_SQL = "UPDATE plane "
            +"SET owner = ?, model = ?, type = ?, platenumber = ?"
            +"WHERE id = ?";
    private static final String SELECT_PLANE_BY_ID_SQL = "SELECT * FROM plane WHERE id = ?";
    private static final String DELETE_PLANE_BY_ID_SQL = "DELETE FROM plane WHERE id = ?";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";
    private static final String SELECT_ALL_PLANES_BY_ID_SQL = "SELECT * FROM plane";


    private static ConnectionFactory connectionFactory;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;
    private PlaneDao planeDao;
    private Plane plane;

    @BeforeEach
    public void init() throws SQLException {
        mockConnection =mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        connectionFactory = mock(ConnectionFactory.class);
        planeDao=new PlaneDao(connectionFactory);
        plane=DataCreationHelper.createPlane();
        when(connectionFactory.getConnection()).thenReturn(mockConnection);
        createMocksFromGetByIdMethod();


    }

    private void createMocksFromGetByIdMethod() throws SQLException {
        when(mockConnection.prepareStatement(SELECT_PLANE_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(plane.getId());
        when(mockResultSet.getInt("id")).thenReturn(plane.getId());
        when(mockResultSet.getString("owner")).thenReturn(plane.getOwner());
        when(mockResultSet.getString("model")).thenReturn(plane.getModel());
        when(mockResultSet.getString("type")).thenReturn(plane.getType());
        when(mockResultSet.getInt("platenumber")).thenReturn(plane.getPlatenumber());

    }

    @Test
    public void whenCreateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, PlaneDaoException {
        when(mockConnection.prepareStatement(INSERT_PLANE_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
        Plane result = planeDao.create(plane);
        assertEquals(plane, result);


    }


    @Test
    public void whenCreateReturnNullGeneratedIdThenDAOExceptionShouldBeThrown() throws SQLException, PlaneDaoException {
        when(mockConnection.prepareStatement(INSERT_PLANE_SQL)).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(false);
        Throwable exception =  assertThrows(PlaneDaoException.class,() -> {
            planeDao.create(plane);
        });

        assertEquals(exception.getMessage(), PLANE_ID_CANNOT_BE_NULL.get());
    }

    @Test
    public void whenUpdateThenCorrectSQLShouldBeExecutedAndCorrectEntityShouldBeReturned() throws SQLException, PlaneDaoException {
        when(mockConnection.prepareStatement(UPDATE_PLANE_SQL)).thenReturn(mockStatement);
        Plane result = planeDao.update(plane);

        assertEquals(plane, result);
    }

    @Test
    public void whenDeleteThenCorrectSQLShouldBeExecutedAndTrueShouldBeReturned() throws PlaneDaoException, SQLException {
        when(mockConnection.prepareStatement(DELETE_PLANE_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);
        boolean result = planeDao.delete(plane);
        assertEquals(true, result);
    }

    @Test
    public void whenDeleteStatementExecuteReturnOThenFalseShouldBeReturned() throws SQLException, PlaneDaoException {
        when(mockConnection.prepareStatement(DELETE_PLANE_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(0);
        boolean result = planeDao.delete(plane);

        assertEquals(false, result);
    }

    @Test
    public void whenGetAllThenCorrectSQLShouldBeExecutedAndCorrectListReturned() throws PlaneDaoException, SQLException {
        when(mockConnection.prepareStatement(SELECT_ALL_PLANES_BY_ID_SQL)).thenReturn(mockStatement);
        when(mockResultSet.next()).thenReturn(true,false);
        List<Plane> planes = planeDao.getAll();

        assertFalse(planes.isEmpty());
    }

    @Test
    public void whenGetByIdReturnEmptyResultSetThenEmptyOptionalShouldBeReturned() throws PlaneDaoException, SQLException {
        when(mockResultSet.next()).thenReturn(false);
        Optional<Plane> plane = planeDao.getById(Integer.valueOf(1));
        assertEquals(Optional.empty(), plane);
    }

    @Test
    public void whenGetByIdNullPassedThenDAOExceptionShouldBeThrown() {
        Throwable exception =  assertThrows(PlaneDaoException.class,() -> {
            planeDao.getById(null);
        });

        assertEquals(PLANE_ID_CANNOT_BE_NULL.get(), exception.getMessage());
    }

    @Test
    public void whenCreateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(PlaneDaoException.class, () -> {
            when(mockConnection.prepareStatement(INSERT_PLANE_SQL)).thenThrow(new SQLException());
            planeDao.create(plane);
        });

        assertEquals(exception.getMessage(), FAILED_TO_CREATE_NEW_PLANE.get());
    }

    @Test
    public void whenUpdateThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(PlaneDaoException.class, () -> {
            when(mockConnection.prepareStatement(UPDATE_PLANE_SQL)).thenThrow(new SQLException());
            planeDao.update(plane);
        });

        assertEquals(exception.getMessage(), FAILED_TO_UPDATE_PLANE_WITH_ID.get()+1);
    }

    @Test
    public void whenDeleteThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(PlaneDaoException.class, () -> {
            when(mockConnection.prepareStatement(DELETE_PLANE_BY_ID_SQL)).thenThrow(new SQLException());
            planeDao.delete(plane);
        });

        assertEquals(exception.getMessage(), FAILED_TO_DELETE_PLANE_WITH_ID.get()+1);
    }

    @Test
    public void whenGetAllThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(PlaneDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_ALL_PLANES_BY_ID_SQL)).thenThrow(new SQLException());
            planeDao.getAll();
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_ALL_PLANES.get());
    }

    @Test
    public void whenGetByIdThrownSQlExceptionThenDAOExceptionShouldBeThrownToo() {
        Throwable exception = assertThrows(PlaneDaoException.class, () -> {
            when(mockConnection.prepareStatement(SELECT_PLANE_BY_ID_SQL)).thenThrow(new SQLException());
            planeDao.getById(Integer.valueOf(1));
        });

        assertEquals(exception.getMessage(), FAILED_TO_GET_PLANE_WITH_ID.get()+1);
    }
}


