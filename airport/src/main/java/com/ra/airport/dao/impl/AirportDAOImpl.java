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
import com.ra.airport.entity.Airport;

import com.ra.airport.factory.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirportDAOImpl implements AirPortDao<Airport> {

    private final transient ConnectionFactory conn;

    private static final int AIRPORT_ID = 6;
    private static final int AIRPORT_NAME = 1;
    private static final int AIRPORT_NUM = 2;
    private static final int AIRPORT_TYPE = 3;
    private static final int AIRPORT_ADDRESSES = 4;
    private static final int AIRPORT_TERMINAL = 5;
    private static final Logger LOGGER = LogManager.getLogger(AirportDAOImpl.class);

    public AirportDAOImpl(final ConnectionFactory conn) {
        this.conn = conn;
    }

    @Override
    public Airport create(Airport airport) throws AirPortDaoException {
        final String query = "INSERT INTO Airport(apname, apnum, aptype, addresses, terminalcount) "
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = conn.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            fillPreparedStatement(airport, statement);
            statement.executeUpdate();
            final ResultSet resultSet = connection.prepareStatement("Select LAST_INSERT_ID() from Airport").executeQuery();
            if (resultSet.next()) {
                airport = getById(resultSet.getInt(1)).get();
            }
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_CREATE_NEW_AIRPORT.get() + airport.getApid();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return airport;
    }

    @Override
    public Airport update(Airport airport) throws AirPortDaoException {
        final String query = "UPDATE Airport SET "
                + " apname = ?,"
                + " apnum = ?,"
                + " aptype = ?,"
                + " addresses = ?,"
                + " terminalcount = ?"
                + " WHERE apid = ?";
        try (Connection connection = conn.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            fillPreparedStatement(airport, statement);
            statement.setInt(AIRPORT_ID, airport.getApid());
            statement.executeUpdate();
            airport = getById(airport.getApid()).get();
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_AIRPORT_WITH_ID.get() + airport.getApid();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return airport;
    }

    @Override
    public boolean delete(final Airport airport) throws AirPortDaoException {
        final String query = "DELETE FROM Airport "
                + "WHERE apid = ?";
        try (Connection connection = conn.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, airport.getApid());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_AIRPORT_WITH_ID.get() + airport.getApid();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    @Override
    public Optional<Airport> getById(final Integer apid) throws AirPortDaoException {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport Where apid = ?";
        Optional<Airport> optionalAirport;
        try (Connection connection = conn.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, apid);
            final ResultSet res = statement.executeQuery();
            if (res.next()) {
                optionalAirport = Optional.of(createAirport(res));
                return optionalAirport;
            }
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_AIRPORT_WITH_ID.get() + apid;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Airport> getAll() throws AirPortDaoException {
        final String query = "Select apname, apnum, aptype, addresses, terminalcount, apid From Airport";
        try (Connection connection = conn.getConnection()) {
            final List<Airport> list = new ArrayList<>();
            final PreparedStatement statement = connection.prepareStatement(query);
            final ResultSet res = statement.executeQuery();
            while (res.next()) {
                list.add(createAirport(res));
            }
            return list;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_ALL_AIRPORTS.get();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    private Airport createAirport(final ResultSet res) throws SQLException {

        return new Airport(res.getInt(AIRPORT_ID),
                res.getString(AIRPORT_NAME),
                res.getInt(AIRPORT_NUM),
                res.getString(AIRPORT_TYPE),
                res.getString(AIRPORT_ADDRESSES),
                res.getInt(AIRPORT_TERMINAL));
    }

    private void fillPreparedStatement(final Airport airport, final PreparedStatement statement) throws SQLException {
        statement.setString(AIRPORT_NAME, airport.getApname());
        statement.setInt(AIRPORT_NUM, airport.getApnum());
        statement.setString(AIRPORT_TYPE, airport.getAptype());
        statement.setString(AIRPORT_ADDRESSES, airport.getAddresses());
        statement.setInt(AIRPORT_TERMINAL, airport.getTerminalcount());
    }
}
