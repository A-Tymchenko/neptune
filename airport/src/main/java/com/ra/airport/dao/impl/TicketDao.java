package com.ra.airport.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Ticket;
import com.ra.airport.factory.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of {@link AirPortDao} interface.
 */
public class TicketDao implements AirPortDao<Ticket> {
    private final transient ConnectionFactory connectionFactory;
    private static final transient Logger LOGGER = LogManager.getLogger(TicketDao.class);

    public TicketDao(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Create {@link Ticket} entity in DB and return it.
     *
     * @param ticket entity to create
     * @return {@link Ticket} entity
     * @throws AirPortDaoException exception for DAO layer
     */
    @Override
    public Ticket create(final Ticket ticket) throws AirPortDaoException {
        final String sql = "INSERT INTO TICKET (TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE) VALUES (?,?,?,?)";
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            fillPreparedStatement(ticket, preparedStatement);
            preparedStatement.executeUpdate();
            final ResultSet scopeResultSet = connection.prepareStatement("SELECT SCOPE_IDENTITY()").executeQuery();
            if (scopeResultSet.next()) {
                ticket.setTicketId(scopeResultSet.getInt(1));
            }
        } catch (SQLException e) {
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
        final String sql = "UPDATE TICKET "
                + "SET TICKET_NUMBER = ?, PASSENGER_NAME = ?, DOCUMENT = ?, SELLING_DATE = ?"
                + "WHERE TICKET_ID = ?";
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            fillPreparedStatement(ticket, preparedStatement);
            preparedStatement.setInt(StatementParameter.TICKET_ID.get(), ticket.getTicketId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
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
        final String sql = "DELETE FROM TICKET WHERE TICKET_ID = ?";
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ticket.getTicketId());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_TICKET_WITH_ID.get() + ticket.getTicketId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return result;
    }

    /**
     * Return {@link Ticket} object from DB or exception if object not exists.
     *
     * @param idTicket ticket id
     * @return {@link Ticket}
     */
    @Override
    public Optional<Ticket> getById(final Integer idTicket) throws AirPortDaoException {
        if (idTicket == null) {
            throw new AirPortDaoException(ExceptionMessage.TICKET_ID_CANNOT_BE_NULL.get());
        }
        final String sql = "SELECT * FROM TICKET WHERE TICKET_ID = ?";
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idTicket);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getTicketFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_TICKET_WITH_ID.get() + idTicket;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return Optional.empty();
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
        final List<Ticket> ticketList = new ArrayList<>();
        final String sql = "SELECT * FROM TICKET";

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Ticket ticket = getTicketFromResultSet(resultSet);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_TICKETS.get();
            LOGGER.error(message, e);
            throw new AirPortDaoException(message, e);
        }
        return ticketList;
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

    /**
     * Fill {@link PreparedStatement} parameters.
     * Get them from {@link Ticket} entity.
     *
     * @param ticket entity
     * @param preparedStatement statement for filling
     * @throws SQLException exception for DAO layer
     */
    private void fillPreparedStatement(final Ticket ticket, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(StatementParameter.TICKET_NUMBER.get(), ticket.getTicketNumber());
        preparedStatement.setString(StatementParameter.PASSENGER_NAME.get(), ticket.getPassengerName());
        preparedStatement.setString(StatementParameter.DOCUMENT.get(), ticket.getDocument());
        preparedStatement.setTimestamp(StatementParameter.SELLING_DATE.get(), ticket.getSellingDate());
    }
}
