package com.ra.courses.airport.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.courses.airport.entity.Ticket;
import org.apache.log4j.Logger;

public class TicketDao implements TicketDaoInterface<Ticket> {
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    private static final transient Logger LOGGER = Logger.getLogger(TicketDao.class);

    private final transient ConnectionFactory connectionFactory;

    public TicketDao(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
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
        ticket.setIdTicket(resultSet.getLong("ID"));
        ticket.setTicketNumber(resultSet.getString("TICKET_NUMBER"));
        ticket.setPassengerName(resultSet.getString("PASSENGER_NAME"));
        ticket.setDocument(resultSet.getString("DOCUMENT"));
        ticket.setSellingDate(resultSet.getTimestamp("SELLING_DATE"));
        return ticket;
    }

    /**
     * Add a new Ticket to db.
     *
     * @param ticket to insert
     * @return count of inserted rows.
     */
    @Override
    public Integer insert(final Ticket ticket) {
        final String sql = "INSERT INTO TICKET "
                         + "(TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE) VALUES (?,?,?,?)";

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(ONE, ticket.getTicketNumber());
            preparedStatement.setString(TWO, ticket.getPassengerName());
            preparedStatement.setString(THREE, ticket.getDocument());
            preparedStatement.setTimestamp(FOUR, ticket.getSellingDate());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error in method insert: " + e);
            }
        }
        return 0;
    }

    /**
     * Returns list of tickets from db.
     *
     * @return list of tickets.
     */
    @Override
    public List<Ticket> getAll() {
        final List<Ticket> ticketList = new ArrayList<>();
        final String sql = "SELECT ID, TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE FROM TICKET";

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Ticket ticket = getTicketFromResultSet(resultSet);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error in method getAll: " + e);
            }
        }
        return ticketList;
    }

    /**
     * Returns a ticket from db by idTicket.
     *
     * @param idTicket of a ticket.
     * @return ticket.
     */
    @Override
    public Optional<Ticket> getById(final Long idTicket) {
        final String sql = "SELECT ID, TICKET_NUMBER, PASSENGER_NAME, DOCUMENT, SELLING_DATE "
                   + "FROM TICKET "
                   + "WHERE ID = ?";

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(ONE, idTicket);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getTicketFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error in method getById: " + e);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns a number of updated tickets.
     *
     * @param ticket to update.
     * @return number of tickets
     */
    @Override
    public Integer update(final Ticket ticket) {
        final String sql = "UPDATE TICKET "
                         + "SET TICKET_NUMBER=?, PASSENGER_NAME=?, DOCUMENT=?, SELLING_DATE=?"
                         + "WHERE ID = ?";

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(ONE, ticket.getTicketNumber());
            preparedStatement.setString(TWO, ticket.getPassengerName());
            preparedStatement.setString(THREE, ticket.getDocument());
            preparedStatement.setTimestamp(FOUR, ticket.getSellingDate());
            preparedStatement.setLong(FIVE, ticket.getIdTicket());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error in method update: " + e);
            }
        }
        return 0;
    }

    /**
     * Returns a number of deleted tickets.
     *
     * @param idTicket to delete.
     * @return number deleted tickets.
     */
    @Override
    public Integer delete(final Long idTicket) {
        final String sql = "DELETE FROM TICKET WHERE ID=?";

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(ONE, idTicket);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error in method delete: " + e);
            }
        }
        return 0;
    }
}
