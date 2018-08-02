package com.ra.airport.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.AirPortDao;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.exception.ExceptionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link AirPortDao} interface.
 */
@Repository("flightDao")
public class FlightDao implements AirPortDao<Flight> {

    private static final String INSERT_FLIGHT_SQL = "INSERT INTO flight "
            + "(name, carrier, duration, meal_on, fare, departure_date, arrival_date) "
            + " VALUES(?,?,?,?,?,?,?)";

    private static final String UPDATE_FLIGHT_SQL = "UPDATE flight "
            + "SET name = ?, carrier = ?, duration = ?, meal_on = ?, fare = ?, departure_date = ?, arrival_date = ? "
            + "WHERE flId = ?";

    private static final Logger LOGGER = LogManager.getLogger(FlightDao.class);

    private final transient JdbcTemplate jdbcTemplate;

    @Autowired
    public FlightDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Create {@link Flight} entity in DB and return it.
     *
     * @param flight entity to create
     * @return {@link Flight} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    public Flight create(final Flight flight) throws AirPortDaoException {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> createPreparedStatement(flight, connection, INSERT_FLIGHT_SQL), keyHolder);
            flight.setFlId((Integer) keyHolder.getKey());
            return flight;
        } catch (DataAccessException e) {
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
    public Flight update(final Flight flight) throws AirPortDaoException {
        try {
            jdbcTemplate.update(connection -> {
                final PreparedStatement preparedStatement = createPreparedStatement(flight, connection, UPDATE_FLIGHT_SQL);
                preparedStatement.setInt(StatementParameter.FLIGHT_ID.get(), flight.getFlId());
                return preparedStatement;
            });
            return flight;
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_FLIGHT_WITH_ID.get() + flight.getFlId();
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
            final int deletedRowCount = jdbcTemplate.update("DELETE FROM flight WHERE flId = ?", flight.getFlId());
            return deletedRowCount > 0;
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_FLIGHT_WITH_ID.get() + flight.getFlId();
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
    public Optional<Flight> getById(final int flightId) throws AirPortDaoException {
        try {
            final BeanPropertyRowMapper<Flight> rowMapper = BeanPropertyRowMapper.newInstance(Flight.class);
            final Flight flight = jdbcTemplate.queryForObject("SELECT * FROM flight WHERE flId = ?", rowMapper, flightId);
            return Optional.ofNullable(flight);
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_FLIGHT_WITH_ID.get() + flightId;
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
            final BeanPropertyRowMapper<Flight> rowMapper = BeanPropertyRowMapper.newInstance(Flight.class);
            return jdbcTemplate.query("SELECT * FROM flight", rowMapper);
        } catch (DataAccessException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_FLIGHTS.get();
            LOGGER.error(message, e);
            throw new AirPortDaoException(message, e);
        }
    }

    /**
     * Create {@link PreparedStatement} and fill parameters.
     * Get them from {@link Flight} entity.
     *
     * @param flight entity
     * @param connection connection for {@link PreparedStatement} instance creation.
     * @return {@link PreparedStatement} instance
     * @throws SQLException exception for DAO layer
     */
    private PreparedStatement createPreparedStatement(final Flight flight, final Connection connection,
                                                      final String sql) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        final LocalDateTime departureDate = flight.getDepartureDate();
        preparedStatement.setString(StatementParameter.FLIGHT_NAME.get(), flight.getName());
        preparedStatement.setString(StatementParameter.FLIGHT_CARRIER.get(), flight.getCarrier());
        preparedStatement.setTime(StatementParameter.FLIGHT_DURATION.get(), Time.valueOf(flight.getDuration()));
        preparedStatement.setBoolean(StatementParameter.FLIGHT_MEAL_ON.get(), flight.getMealOn());
        preparedStatement.setDouble(StatementParameter.FLIGHT_FARE.get(), flight.getFare());
        preparedStatement.setTimestamp(StatementParameter.FLIGHT_DEPARTURE_DATE.get(), Timestamp.valueOf(departureDate));
        preparedStatement.setTimestamp(StatementParameter.FLIGHT_ARRIVAL_DATE.get(), Timestamp.valueOf(flight.getArrivalDate()));

        return preparedStatement;
    }
}