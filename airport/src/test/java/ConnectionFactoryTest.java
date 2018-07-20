import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ra.courses.airport.dao.impl.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionFactoryTest {

    private ConnectionFactory connectionFactory;


    @BeforeEach
    public void beforeTest() throws IOException {
        connectionFactory = ConnectionFactory.getInstance();
    }

    @Test
    public void whenGetConnectionNewConnectionShouldBeReturned() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    public void whenConnectionFactoryGetInstanceCallTwiceTheSameInstanceShouldBeReturned() throws IOException {
        ConnectionFactory firstInstance = ConnectionFactory.getInstance();
        ConnectionFactory secondInstance = ConnectionFactory.getInstance();
        assertTrue(firstInstance == secondInstance);
    }
}