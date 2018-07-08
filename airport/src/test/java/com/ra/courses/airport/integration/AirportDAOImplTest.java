package com.ra.courses.airport.integration;

import com.ra.courses.airport.dao.impl.AirportDAOImpl;
import com.ra.courses.airport.dao.impl.ConnectionFactory;
import com.ra.courses.airport.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AirportDAOImplTest {

    ConnectionFactory conn;
    Airport ap;

    {
        try {
            conn = ConnectionFactory.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void initH2(){
        try {
            conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/dbschema.sql'");
            conn.getConnection().createStatement().executeUpdate("RUNSCRIPT FROM 'src/main/resources/test-data.sql'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ap = new Airport("10293","Kenedy", 4949034, "International", "USA New Yourk", 10);
    }

    @Test
    public void addAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        apim.addAirport(ap);
    }

    @Test
    public void updateAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        apim.updateAirport(ap);
    }

    @Test
    public void deleteAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        apim.deleteAirport(ap);
    }

    @Test
    public void testGetAirport() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        Optional<Airport> ap = apim.getAirport("7");
        Optional<Airport> ap1 = apim.getAirport("9");
    }

    @Test
    public void getAirports() {
        AirportDAOImpl apim = new AirportDAOImpl(conn);
        List<Airport> list = apim.getAirports();

    }

    @Test
    public void testSetMethodsAirport (){
        Airport ap = new Airport("10293","Kenedy", 4949034, "International", "USA New Yourk", 10);
        ap.setAddresses("test");
        ap.setApid("fsvfvdfvdf");
        ap.setApname("dsfdsfdsf");
        ap.setApnum(332423);
        ap.setAptype("fsdfsdfsdf");
        ap.setTerminalcount(4);
    }


}