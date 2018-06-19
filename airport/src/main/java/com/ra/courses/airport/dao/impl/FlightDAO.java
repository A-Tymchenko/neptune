package com.ra.courses.airport.dao.impl;

import com.ra.courses.airport.dao.DAO;
import com.ra.courses.airport.entity.Flight;
import com.ra.courses.airport.factory.ConnectionFactory;
import com.ra.courses.airport.mapper.FlightRowMapper;
import com.ra.courses.airport.mapper.RowMapper;

import java.sql.*;


/**
 * Created by anbo06131 on 6/15/2018.
 */
public class FlightDAO implements DAO <Flight> {

    private ConnectionFactory connectionFactory;

    private static final String INSERT_FLIGHT_WITH_DEFAULT_VALUES_SQL = "INSERT INTO Flight DEFAULT VALUES";
    private static final String SELECT_FLIGHT_BY_ID_SQL = "SELECT * FROM Flight WHERE id=?";

    public FlightDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Flight create() {
        Flight flight = null;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FLIGHT_WITH_DEFAULT_VALUES_SQL,new String[] {"id"})) {

            preparedStatement.executeUpdate();

            ResultSet idResultSet = preparedStatement.getGeneratedKeys();
            Integer id = idResultSet != null && idResultSet.next() ? idResultSet.getInt("id") : null;
            if (id != null) {
                flight = getById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
        }
        return flight;
    }

    private Flight getById(Integer id) {
        Flight flight = null;
        if (id == null) {
            throw new IllegalArgumentException("Flight id shouldn't be null");
        }
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = getPrepareStatement(connection,SELECT_FLIGHT_BY_ID_SQL, id);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            flight = new Flight();
            RowMapper<Flight> rowMapper = new FlightRowMapper();
            rowMapper.mapRow(resultSet, flight);
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
        }
        return flight;
    }

    public static PreparedStatement getPrepareStatement(Connection connection, String sql, Integer id) throws SQLException {
        PreparedStatement result = connection.prepareStatement(sql);
        result.setInt(1,id);
        return result;
    }

    public Flight update(Flight flight) {
        return null;
    }

    public boolean delete(Integer id) {
        return false;
    }
}
