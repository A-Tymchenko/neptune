package com.ra.airport;

import com.ra.airport.configuration.AirPortConfiguration;
import com.ra.airport.dao.springimpl.AirportDAOImpl;
import com.ra.airport.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AirPortConfiguration.class})
class AirportDAOSpringImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    Airport airport;
    @Autowired
    AirportDAOImpl airportImpl;

    @BeforeEach
    public void initH2() throws SQLException, IOException {
        jdbcTemplate.update("RUNSCRIPT FROM 'src/test/resources/sql/dbschema.sql'");
        jdbcTemplate.update("RUNSCRIPT FROM 'src/test/resources/sql/test-data.sql'");
        airport = new Airport(1,"Kenedy", 12345, "international", "New York", 10);
    }

    @Test
    void create() {
        airportImpl.create(airport);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getById() {
        System.out.println(airportImpl.getById(1));
    }

    @Test
    void getAll() {
        System.out.println(airportImpl.getAll());
    }
}