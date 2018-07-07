package com.ra.shop.Utils;

import com.ra.shop.utils.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

public abstract class SingletonTest<S> {
    /**
     * The singleton's getInstance method
     */
    private final Supplier<S> singletonInstanceMethod;

    /**
     * Create a new singleton test instance using the given 'getInstance' method.
     *
     * @param singletonInstanceMethod The singleton's getInstance method
     */
    public SingletonTest(final Supplier<S> singletonInstanceMethod) {
        this.singletonInstanceMethod = singletonInstanceMethod;
    }

    /**
     * Test the singleton in a non-concurrent setting.
     */
    @Test
    public void testMultipleCallsReturnTheSameObjectInSameThread() throws IOException {
        // Create several instances in the same calling thread
        S instance1 = this.singletonInstanceMethod.get();
        S instance2 = this.singletonInstanceMethod.get();
        S instance3 = this.singletonInstanceMethod.get();
        // now check they are equal
        assertSame(instance1, instance2);
        assertSame(instance1, instance3);
        assertSame(instance2, instance3);
    }

    /**
     * Test singleton instance in a concurrent setting.
     */
    @Test
    public void testMultipleCallsReturnTheSameObjectInDifferentThreads() throws Exception, IOException {
        assertTimeout(ofMillis(10000), () -> {
            // Create 10000 tasks and inside each callable instantiate the singleton class
            final List<Callable<S>> tasks = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                tasks.add(this.singletonInstanceMethod::get);
            }

            // Use up to 8 concurrent threads to handle the tasks
            final ExecutorService executorService = Executors.newFixedThreadPool(8);
            final List<Future<S>> results = executorService.invokeAll(tasks);

            // wait for all of the threads to complete
            final S expectedInstance = this.singletonInstanceMethod.get();
            for (Future<S> res : results) {
                final S instance = res.get();
                assertNotNull(instance);
                assertSame(expectedInstance, instance);
            }

            // tidy up the executor
            executorService.shutdown();
        });
    }

    /**
     * Test ConnectionFactory 'getConnect' method.
     */
    @Nested
    public class ConnectingTests {

        private ConnectionFactory connectionFactory;

        @BeforeEach
        public void createConnectionFactory() throws IOException {
            connectionFactory = ConnectionFactory.getInstance();
        }

        @AfterEach
        public void deleteConnectionFactory() {
            connectionFactory = null;
        }

        @Test
        public void whenReturnConnectionInstanceConnectionClass() throws SQLException {

            assertTrue(connectionFactory.getConnection() instanceof Connection);
        }

        @Test
        public void whenReturnNewConnectionStringEqualsPreviosConnectionString() throws IOException, SQLException {
            String connection = connectionFactory.getConnection().toString();
            connectionFactory = null;
            String newConnection = ConnectionFactory.getInstance().getConnection().toString();

            assertEquals(connection.substring(connection.indexOf(':')),
                newConnection.substring((newConnection.indexOf(':'))));
        }
    }
}
