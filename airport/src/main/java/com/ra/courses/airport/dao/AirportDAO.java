package com.ra.courses.airport.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ra.courses.airport.entity.Airport;

/**
 * @author prorock.
 */
public interface AirportDAO {

    /**
     * Method add new airport.
     * @param airport the airport
     * @throws IOException the exception
     * @throws SQLException the exception
     */
    void addAirport(Airport airport);

    /**
     * Method update airport.
     * @param airport the airport
     * @throws IOException the exception
     * @throws SQLException the exception
     */
    void updateAirport(Airport airport);

    /**
     * Method delete airport.
     * @param airport the airport
     * @throws IOException the exception
     * @throws SQLException the exception
     */
    void deleteAirport(Airport airport);

    /**
     * Method return airport.
     * @return airport
     * @throws IOException the exception
     * @throws SQLException the exception
     */
    Optional<Airport> getAirport(String apid);

    /**
     * Method return airport list.
     * @return list the airport
     * @throws IOException the exception
     * @throws SQLException the exception
     */
    List<Airport> getAirports();

}
