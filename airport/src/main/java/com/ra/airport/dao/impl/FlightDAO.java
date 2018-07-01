package com.ra.airport.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.AirPortDAO;
import com.ra.airport.dao.exception.DAOException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.mapper.FlightRowMapper;
import com.ra.airport.mapper.RowMapper;

/**
 * Implementation of {@link AirPortDAO} interface.
 */
public class FlightDAO implements AirPortDAO<Flight> {

    private static final int NAME = 1;
    private static final int CARRIER = 2;
    private static final int DURATION = 3;
    private static final int MEAL_ON = 4;
    private static final int FARE = 5;
    private static final int DEPARTURE_DATE = 6;
    private static final int ARRIVAL_DATE = 7;
    private static final int FLIGHT_ID = 8;

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight "
            + "(name, carrier, duration, mealOn, fare, departure_date, arrival_date) "
            + " VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight "
            + "SET name = ?, carrier = ?, duration = ?, mealOn = ?, fare = ?, departure_date = ?, arrival_date = ? "
            + "WHERE id = ?";
    private static final String GET_FLIGHT_BY_ID = "SELECT * FROM flight WHERE id = ?";
    private static final String DELETE_FLIGHT = "DELETE FROM flight WHERE id = ?";
    private static final String GET_ALL_FLIGHTS = "SELECT * FROM flight";
    private static final String GET_FLIGHT_ID = "SELECT SCOPE_IDENTITY()";

    private static ConnectionFactory connectionFactory;

    public FlightDAO(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Create {@link Flight} entity in DB and return it.
     * @param flight entity to create
     * @return {@link Flight} entity
     * @throws DAOException exception for AirPortDAO layer
     */
    public Flight create(Flight flight) throws DAOException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement insertPS = connection.prepareStatement(INSERT_FLIGHT_SQL);
            final PreparedStatement selectPS = connection.prepareStatement(GET_FLIGHT_ID);
            fillPreparedStatement(flight, insertPS);
            insertPS.executeUpdate();
            final ResultSet generatedIdRS = selectPS.executeQuery();
            Optional<Integer> flightId;
            if (generatedIdRS.next()) {
                flightId =  Optional.of(generatedIdRS.getInt(1));
            } else {
                flightId = Optional.empty();
            }
            flight = getById(flightId);
        } catch (SQLException e) {
            throw new DAOException(ExceptionMessage.FAILED_TO_CREATE_NEW_FLIGHT.get());
        }
        return flight;
    }

    /**
     * Update {@link Flight} entity in DB and return it.
     *
     * @param flight entity to update
     * @return {@link Flight} entity
     * @throws DAOException exception for AirPortDAO layer
     */
    public Flight update(final Flight flight) throws DAOException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FLIGHT_SQL);
            fillPreparedStatement(flight, preparedStatement);
            preparedStatement.executeUpdate();
            getById(Optional.of(flight.getId()));
        } catch (SQLException e) {
            throw new DAOException(ExceptionMessage.FAILED_TO_UPDATE_FLIGHT_WITH_ID.get() + flight.getId());
        }
        return flight;
    }

    /**
     * Delete {@link Flight} entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param flight entity to delete
     * @return boolean flag
     * @throws DAOException exception for AirPortDAO layer
     */
    public boolean delete(final Flight flight) throws DAOException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FLIGHT);
            preparedStatement.setInt(1, flight.getId());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException(ExceptionMessage.FAILED_TO_DELETE_FLIGHT_WITH_ID.get() + flight.getId());
        }
        return result;
    }

    /**
     * Return {@link Flight} object from DB or exception if object not exists.
     *
     * @param flightId flight id
     * @return {@link Flight}
     */
    public Flight getById(final Optional<Integer> flightId) throws DAOException {
        if (!flightId.isPresent()) {
            throw new DAOException(ExceptionMessage.FLIGHT_ID_CANNOT_BE_NULL.get());
        }
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_FLIGHT_BY_ID);
            preparedStatement.setInt(1, flightId.get());
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Flight flight = new Flight();
                final RowMapper<Flight> rowMapper = new FlightRowMapper();
                flight = rowMapper.mapRow(resultSet, flight);
                return flight;
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionMessage.FAILED_TO_GET_FLIGHT_WITH_ID.get() + flightId.get());
        }
        return null;
    }

    /**
     * Return all entities from DB by {@link Flight} type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws DAOException exception for AirPortDAO layer
     */
    @Override
    public List<Flight> getAll() throws DAOException {
        final List<Flight> flights = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FLIGHTS);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                createFlight(flights, resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(ExceptionMessage.FAILED_TO_GET_ALL_FLIGHTS.get());
        }
        return flights;
    }

    private void createFlight(final List<Flight> flights, final ResultSet resultSet) throws SQLException {
        Flight flight = new Flight();
        final RowMapper<Flight> rowMapper = new FlightRowMapper();
        flight = rowMapper.mapRow(resultSet, flight);
        flights.add(flight);
    }

    /**
     * Fill {@link PreparedStatement} parameters.
     * Get them from {@link Flight} entity.
     *
     * @param flight entity
     * @param preparedStatement statement for filling
     * @throws SQLException exception for AirPortDAO layer
     */
    private void fillPreparedStatement(final Flight flight, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(NAME, flight.getName());
        preparedStatement.setString(CARRIER, flight.getCarrier());
        preparedStatement.setTime(DURATION, Time.valueOf(flight.getDuration()));
        preparedStatement.setBoolean(MEAL_ON, flight.isMealOn());
        preparedStatement.setDouble(FARE, flight.getFare());
        preparedStatement.setTimestamp(DEPARTURE_DATE, Timestamp.valueOf(flight.getDepartureDate()));
        preparedStatement.setTimestamp(ARRIVAL_DATE, Timestamp.valueOf(flight.getArrivalDate()));
        if (flight.getId() != null) {
            preparedStatement.setInt(FLIGHT_ID, flight.getId());
        }
    }
}