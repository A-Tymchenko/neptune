package com.ra.airport.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link AirPortDao} interface.
 */
@Repository
public class TicketDao  implements AirPortDao<Ticket> {
    private static final Logger LOGGER = LogManager.getLogger(TicketDao.class);

    @Autowired
    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TicketDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO TICKET "
            + "(TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE) "
            + "VALUES (:ticketNumber, :passengerName, :document, :sellingDate)";
    private static final String UPDATE_SQL = "UPDATE TICKET "
            + "SET TICKET_NUMBER = :ticketNumber, "
            + "PASSENGER_NAME = :passengerName, "
            + "DOCUMENT = :document, "
            + "SELLING_DATE = :sellingDate "
            + "WHERE TICKET_ID = :ticketId";
    private static final String DELETE_SQL = "DELETE FROM TICKET WHERE TICKET_ID = :ticketId";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM TICKET WHERE TICKET_ID = :ticketId";
    private static final String SELECT_ALL_SQL = "SELECT * FROM TICKET";

    /**
     * Create {@link Ticket} entity in DB and return it.
     *
     * @param ticket entity to create
     * @return {@link Ticket} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public Ticket create(final Ticket ticket) throws AirPortDaoException {
        try {
            final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(INSERT_SQL,
                    new BeanPropertySqlParameterSource(ticket), keyHolder, new String[] {"ID"});
            ticket.setTicketId(keyHolder.getKey().intValue());
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_TICKET.toString(), e);
            throw new AirPortDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_TICKET.get(), e);
        }
        LOGGER.debug("Ticket with id was created {}", ticket.getTicketId());
        return ticket;
    }

    /**
     * Update {@link Ticket} entity in DB and return it.
     *
     * @param ticket entity to update
     * @return {@link Ticket} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public Ticket update(final Ticket ticket) throws AirPortDaoException {
        try {
            namedParameterJdbcTemplate.update(UPDATE_SQL,
                    new BeanPropertySqlParameterSource(ticket));
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_TICKET_WITH_ID.get() + ticket.getTicketId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        LOGGER.debug("Ticket with id was updated {}", ticket.getTicketId());
        return ticket;
    }

    /**
     * Delete {@link Ticket} entity in DB.
     * And return true if operation was successful or false if not.
     *
     * @param ticket entity to delete
     * @return boolean flag
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public boolean delete(final Ticket ticket) throws AirPortDaoException {
        try {
            return namedParameterJdbcTemplate.update(DELETE_SQL,
                    new MapSqlParameterSource("ticketId", ticket.getTicketId())) > 0;
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_TICKET_WITH_ID.get() + ticket.getTicketId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    /**
     * Return {@link Ticket} object from DB or exception if object not exists.
     *
     * @param idTicket ticket id
     * @return {@link Ticket}
     */
    @Override
    public Optional<Ticket> getById(final Integer idTicket) throws AirPortDaoException {
        try {
            return Optional.of(namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                    new MapSqlParameterSource("ticketId", idTicket),
                    (resultSet, i) -> { return getTicketFromResultSet(resultSet); }));
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_TICKET_WITH_ID.get() + idTicket;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    /**
     * Return all entities from DB by {@link Ticket} type.
     * If entities absent in DB return empty {@link List}.
     *
     * @return List entities
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public List<Ticket> getAll() throws AirPortDaoException {
        try {
            return namedParameterJdbcTemplate.query(SELECT_ALL_SQL,
                    (resultSet, i) -> { return getTicketFromResultSet(resultSet); });
        } catch (DataAccessException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_TICKETS.get();
            LOGGER.error(message, e);
            throw new AirPortDaoException(message, e);
        }
    }

    /**
     * Extract a ticket from resultSet.
     *
     * @param resultSet to get ticket.
     * @return ticket.
     * @throws SQLException thrown
     */
    private Ticket getTicketFromResultSet(final ResultSet resultSet) throws SQLException {
        final Ticket ticket = new Ticket();
        ticket.setTicketId(resultSet.getInt("TICKET_ID"));
        ticket.setTicketNumber(resultSet.getString("TICKET_NUMBER"));
        ticket.setPassengerName(resultSet.getString("PASSENGER_NAME"));
        ticket.setDocument(resultSet.getString("DOCUMENT"));
        ticket.setSellingDate(resultSet.getTimestamp("SELLING_DATE"));
        return ticket;
    }
}
