package com.ra.courses.airport.integration;

import com.ra.courses.airport.dao.impl.AbstractDao;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Author: anbo
 * Date: 18.06.2018
 */
public class AbstractDaoTest extends AbstractDao {

    @Test
    void whenGetConnectionNewConnectionShouldBeReturned() throws Exception {
        Connection connection = getConnection();
        assertNotNull(connection);
    }



}
