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

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Flight;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.mapper.FlightRowMapper;
import com.ra.airport.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link AirPortDao} interface.
 */
@Component
public class FlightDao implements AirPortDao<Flight> {

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight "
            + "(name, carrier, duration, meal_on, fare, departure_date, arrival_date) "
            + " VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight "
            + "SET name = ?, carrier = ?, duration = ?, meal_on = ?, fare = ?, departure_date = ?, arrival_date = ? "
            + "WHERE id = ?";

    private static final Logger LOGGER = LogManager.getLogger(FlightDao.class);

    public FlightDao() {
    }

    /**
     * Create {@link Flight} entity in DB and return it.
     *
     * @param flight entity to create
     * @return {@link Flight} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    public Flight create(Flight flight) throws AirPortDaoException {
//        try (Connection connection = connectionFactory.getConnection()) {
//            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FLIGHT_SQL);
//            fillPreparedStatement(flight, preparedStatement);
//            preparedStatement.executeUpdate();
//            final ResultSet generatedIdRS = connection.prepareStatement("SELECT SCOPE_IDENTITY()").executeQuery();
//            Integer flightId = null;
//            if (generatedIdRS.next()) {
//               flightId = generatedIdRS.getInt(1);
//            }
//            flight = getById(flightId).get();
//        } catch (SQLException e) {
//            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_FLIGHT.toString(), e);
//            throw new AirPortDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_FLIGHT.get(), e);
//        }
//        LOGGER.debug("Flight with id was created {}", flight.getIdentifier());
//        return flight;
        return null;
    }

    /**
     * Update {@link Flight} entity in DB and return it.
     *
     * @param flight entity to update
     * @return {@link Flight} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    public Flight update(Flight flight) throws AirPortDaoException {
//        try (Connection connection = connectionFactory.getConnection()) {
//            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FLIGHT_SQL);
//            fillPreparedStatement(flight, preparedStatement);
//            preparedStatement.executeUpdate();
//            flight = getById(flight.getIdentifier()).get();
//        } catch (SQLException e) {
//            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_FLIGHT_WITH_ID.get() + flight.getIdentifier();
//            LOGGER.error(errorMessage, e);
//            throw new AirPortDaoException(errorMessage, e);
//        }
//        LOGGER.debug("Flight with id was updated {}", flight.getIdentifier());
        return flight;
    }

    /**
     * Delete {@link Flight} entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param flight entity to delete
     * @return boolean flag
     * @throws AirPortDaoException exception for DAO layer
     */
    public boolean delete(final Flight flight) throws AirPortDaoException {
        boolean result;
//        try (Connection connection = connectionFactory.getConnection()) {
//            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM flight WHERE id = ?");
//            preparedStatement.setInt(1, flight.getIdentifier());
//            result = preparedStatement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_FLIGHT_WITH_ID.get() + flight.getIdentifier();
//            LOGGER.error(errorMessage, e);
//            throw new AirPortDaoException(errorMessage, e);
//        }
        return false;
    }

    /**
     * Return {@link Flight} object from DB or exception if object not exists.
     *
     * @param flightId flight id
     * @return {@link Flight}
     */
    @Override
    public Optional<Flight> getById(final Integer flightId) throws AirPortDaoException {
        if (flightId == null) {
            throw new AirPortDaoException(ExceptionMessage.FLIGHT_ID_CANNOT_BE_NULL.get());
        }
//        try (Connection connection = connectionFactory.getConnection()) {
//            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM flight WHERE id = ?");
//            preparedStatement.setInt(1, flightId);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                final Flight flight = new FlightRowMapper().mapRow(resultSet, new Flight());
//                return Optional.of(flight);
//            }
//        } catch (SQLException e) {
//            final String errorMessage = ExceptionMessage.FAILED_TO_GET_FLIGHT_WITH_ID.get() + flightId;
//            LOGGER.error(errorMessage, e);
//            throw new AirPortDaoException(errorMessage, e);
//        }
        return Optional.empty();
    }

    /**
     * Return all entities from DB by {@link Flight} type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public List<Flight> getAll() throws AirPortDaoException {
        final List<Flight> flights = new ArrayList<>();
//        try (Connection connection = connectionFactory.getConnection()) {
//            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM flight");
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                createFlight(flights, resultSet);
//            }
//        } catch (SQLException e) {
//            final String message = ExceptionMessage.FAILED_TO_GET_ALL_FLIGHTS.get();
//            LOGGER.error(message, e);
//            throw new AirPortDaoException(message, e);
//        }
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
     * @throws SQLException exception for DAO layer
     */
    private void fillPreparedStatement(final Flight flight, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(StatementParameter.NAME.get(), flight.getName());
        preparedStatement.setString(StatementParameter.CARRIER.get(), flight.getCarrier());
        preparedStatement.setTime(StatementParameter.DURATION.get(), Time.valueOf(flight.getDuration()));
        preparedStatement.setBoolean(StatementParameter.MEAL_ON.get(), flight.getMealOn());
        preparedStatement.setDouble(StatementParameter.FARE.get(), flight.getFare());
        preparedStatement.setTimestamp(StatementParameter.DEPARTURE_DATE.get(), Timestamp.valueOf(flight.getDepartureDate()));
        preparedStatement.setTimestamp(StatementParameter.ARRIVAL_DATE.get(), Timestamp.valueOf(flight.getArrivalDate()));
        if (flight.getIdentifier() != null) {
            preparedStatement.setInt(StatementParameter.IDENTIFIER.get(), flight.getIdentifier());
        }
    }
}