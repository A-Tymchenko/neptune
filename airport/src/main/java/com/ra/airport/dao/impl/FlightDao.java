package com.ra.airport.dao.impl;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Flight;
import com.ra.airport.mapper.FlightRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of {@link AirPortDao} interface.
 */
@Repository
public class FlightDao implements AirPortDao<Flight> {

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight "
            + "(name, carrier, duration, meal_on, fare, departure_date, arrival_date) "
            + " VALUES(?,?,?,?,?,?,?)";

    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight "
            + "SET name = ?, carrier = ?, duration = ?, meal_on = ?, fare = ?, departure_date = ?, arrival_date = ? "
            + "WHERE id = ?";

    private static final Logger LOGGER = LogManager.getLogger(FlightDao.class);

    private FlightRowMapper rowMapper;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FlightDao(JdbcTemplate jdbcTemplate, FlightRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    /**
     * Create {@link Flight} entity in DB and return it.
     *
     * @param flight entity to create
     * @return {@link Flight} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    public Flight create(final Flight flight) throws AirPortDaoException {
        try {
            jdbcTemplate.update(INSERT_FLIGHT_SQL, ps -> fillPreparedStatement(flight, ps));
            Integer flightId = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
            return getById(flightId).orElse(flight);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_FLIGHT.toString(), e);
            throw new AirPortDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_FLIGHT.get(), e);
        }
    }

    /**
     * Update {@link Flight} entity in DB and return it.
     *
     * @param flight entity to update
     * @return {@link Flight} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    public Flight update(Flight flight) throws AirPortDaoException {
        try {
            jdbcTemplate.update(UPDATE_FLIGHT_SQL, ps -> fillPreparedStatement(flight, ps));
            return getById(flight.getIdentifier()).orElse(flight);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_FLIGHT_WITH_ID.get() + flight.getIdentifier();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
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
        try {
            int deletedRowCount = jdbcTemplate.update("DELETE FROM flight WHERE id = ?", flight.getIdentifier());
            return deletedRowCount > 0;
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_FLIGHT_WITH_ID.get() + flight.getIdentifier();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    /**
     * Return {@link Flight} object from DB or exception if object not exists.
     *
     * @param flightId flight id
     * @return {@link Flight}
     */
    @Override
    public Optional<Flight> getById(final Integer flightId) throws AirPortDaoException {
        final String errorMessage = ExceptionMessage.FAILED_TO_GET_FLIGHT_WITH_ID.get() + flightId;
        if (flightId == null) {
            throw new AirPortDaoException(ExceptionMessage.FLIGHT_ID_CANNOT_BE_NULL.get());
        }
        try {
            Flight flight = jdbcTemplate.queryForObject("SELECT * FROM flight WHERE id = ?", Arrays.asList(flightId).toArray(),rowMapper);
            return Optional.ofNullable(flight);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
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
        try {
            return jdbcTemplate.query("SELECT * FROM flight", rowMapper);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_FLIGHTS.get();
            LOGGER.error(message, e);
            throw new AirPortDaoException(message, e);
        }
    }

    /**
     * Fill {@link PreparedStatement} parameters.
     * Get them from {@link Flight} entity.
     *
     * @param flight            entity
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