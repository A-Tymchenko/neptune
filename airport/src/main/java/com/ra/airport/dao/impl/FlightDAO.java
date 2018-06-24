package com.ra.airport.dao.impl;

import com.ra.airport.dao.DAO;
import com.ra.airport.dao.exception.DAOException;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.mapper.FlightRowMapper;
import com.ra.airport.mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of {@link DAO} interface.
 */
public class FlightDAO implements DAO<Flight> {

    private static final int NAME = 1;
    private static final int CARRIER = 2;
    private static final int DURATION = 3;
    private static final int MEAL_ON = 4;
    private static final int FARE = 5;
    private static final int DEPARTURE_DATE = 6;
    private static final int ARRIVAL_DATE = 7;
    private static final int ID = 8;
    private static final String FLIGHT_ID_CAN_NOT_BE_NULL = "Flight id can't be null";

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight " +
            "(name, carrier, duration, meal, fare, departure_date, arrival_date) " +
            " VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight SET name = ?, carrier = ?, duration = ?, meal = ?, fare = ?, departure_date = ?, arrival_date = ? WHERE id = ?";
    private static final String SELECT_FLIGHT_BY_ID_SQL = "SELECT * FROM flight WHERE id = ?";
    private static final String DELETE_FLIGHT_BY_ID_SQL = "DELETE FROM flight WHERE id = ?";
    private static final String SELECT_ALL_FLIGHTS_SQL ="SELECT * FROM flight";
    private static final String SELECT_LAST_GENERATED_ID_SQL = "SELECT SCOPE_IDENTITY()";

    private ConnectionFactory connectionFactory;

    public FlightDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Flight create(Flight flight) throws DAOException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement insertPS = connection.prepareStatement(INSERT_FLIGHT_SQL);
            PreparedStatement selectPS = connection.prepareStatement(SELECT_LAST_GENERATED_ID_SQL);
            fillPreparedStatement(flight, insertPS);
            insertPS.executeUpdate();
            ResultSet generatedIdRS = selectPS.executeQuery();
            Optional<Integer> id = generatedIdRS.next() ?
                    Optional.of(generatedIdRS.getInt(1)) : Optional.empty();
            flight = getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
        }
        return flight;
    }

    public Flight update(Flight flight) throws DAOException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FLIGHT_SQL);
            fillPreparedStatement(flight, preparedStatement);
            preparedStatement.executeUpdate();
            getById(Optional.of(flight.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
            throw new DAOException(e.getMessage());
        }
        return flight;
    }

    public boolean delete(Flight flight) throws DAOException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FLIGHT_BY_ID_SQL);
            preparedStatement.setInt(1, flight.getId());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
            throw new DAOException(e.getMessage());
        }
        return result;
    }

    /**
     * Fill {@link PreparedStatement} parameters.
     * Get them from {@link Flight} entity.
     *
     * @param flight
     * @param preparedStatement
     * @throws SQLException
     */
    private void fillPreparedStatement(Flight flight, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, flight.getName());
        preparedStatement.setString(CARRIER, flight.getCarrier());
        preparedStatement.setTime(DURATION, Time.valueOf(flight.getDuration()));
        preparedStatement.setBoolean(MEAL_ON, flight.isMealOn());
        preparedStatement.setDouble(FARE, flight.getFare());
        preparedStatement.setTimestamp(DEPARTURE_DATE, Timestamp.valueOf(flight.getDepartureDate()));
        preparedStatement.setTimestamp(ARRIVAL_DATE, Timestamp.valueOf(flight.getArrivalDate()));
        if (flight.getId() != null) {
            preparedStatement.setInt(ID, flight.getId());
        }
    }

    /**
     * Return object from DB or exception if object not exists.
     *
     * @param id
     * @return {@link Flight}
     */
    public Flight getById(Optional<Integer> id) throws DAOException {
        Flight flight = null;
        if (!id.isPresent()) {
            throw new DAOException(FLIGHT_ID_CAN_NOT_BE_NULL);
        }
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FLIGHT_BY_ID_SQL);
            preparedStatement.setInt(1, id.get());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                flight = new Flight();
                RowMapper<Flight> rowMapper = new FlightRowMapper();
                flight = rowMapper.mapRow(resultSet, flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
            throw new DAOException(e.getMessage());
        }
        return flight;
    }

    @Override
    public List<Flight> getAll() throws DAOException {
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FLIGHTS_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight();
                RowMapper<Flight> rowMapper = new FlightRowMapper();
                flight = rowMapper.mapRow(resultSet, flight);
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo add logging here
            throw new DAOException(e.getMessage());
        }
        return flights;
    }
}