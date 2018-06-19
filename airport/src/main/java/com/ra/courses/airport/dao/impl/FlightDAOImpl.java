package com.ra.courses.airport.dao.impl;

import com.ra.courses.airport.dao.FlightDAO;
import com.ra.courses.airport.entity.Flight;
import com.ra.courses.airport.factory.ConnectionFactory;
import com.ra.courses.airport.mapper.FlightRowMapper;
import com.ra.courses.airport.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by anbo06131 on 6/15/2018.
 */
public class FlightDAOImpl implements FlightDAO {

    private ConnectionFactory connectionFactory;

    private static final String INSERT_FLIGHT_WITH_DEFAULT_VALUES_SQL = "INSERT INTO Flight DEFAULT VALUES";
    private static final String SELECT_FLIGHT_SQL = "SELECT * FROM Flight WHERE id=?";

    public FlightDAOImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Flight create() {
        Flight flight = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FLIGHT_WITH_DEFAULT_VALUES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             RowMapper<Flight> rowMapper = new FlightRowMapper();
             flight = new Flight();
             rowMapper.mapRow(resultSet, flight);
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
        }
        return flight;
    }

//    public Flight getById(Integer id) {
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FLIGHT_WITH_DEFAULT_VALUES_SQL);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//        } catch (SQLException e) {
//        e.printStackTrace();
//        //todo add logging here
//    }
//        Flight flight = null;
//        RowMapper<Flight> rowMapper = new FlightRowMapper();
//        flight = new Flight();
//        rowMapper.mapRow(resultSet, flight);
//    }

    public Flight update(Flight flight) {
        return null;
    }

    public boolean delete(Integer id) {
        return false;
    }
}
