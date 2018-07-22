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

    private final transient ConnectionFactory connectionFactory;

    private static final Logger LOGGER = LogManager.getLogger(AirportDAOImpl.class);

    public AirportDAOImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Airport create(Airport airport) throws AirPortDaoException {
        final String query = "INSERT INTO Airport(apname, apnum, aptype, address, terminalcount) "
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            fillPreparedStatement(airport, statement);
            statement.executeUpdate();
            final ResultSet resultSet = connection.prepareStatement("Select LAST_INSERT_ID() from Airport").executeQuery();
            if (resultSet.next()) {
                airport = getById(resultSet.getInt(1)).get();
            }
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_CREATE_NEW_AIRPORT.get() + airport.getApId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return airport;
    }

    @Override
    public Airport update(Airport airport) throws AirPortDaoException {
        final String query = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, address = ?, terminalcount = ?"
                + " WHERE apid = ?";
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            fillPreparedStatement(airport, statement);
            statement.setInt(StatementParameter.AIRPORT_ID.get(), airport.getApId());
            statement.executeUpdate();
            airport = getById(airport.getApId()).get();
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_AIRPORT_WITH_ID.get() + airport.getApId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return airport;
    }

    @Override
    public boolean delete(final Airport airport) throws AirPortDaoException {
        final String query = "DELETE FROM Airport "
                + "WHERE apid = ?";
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, airport.getApId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_AIRPORT_WITH_ID.get() + airport.getApId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    @Override
    public Optional<Airport> getById(final Integer apid) throws AirPortDaoException {
        final String query = "Select * From Airport Where apid = ?";
        Optional<Airport> optionalAirport;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, apid);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalAirport = Optional.of(createAirport(resultSet));
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
        final String query = "Select * From Airport";
        try (Connection connection = connectionFactory.getConnection()) {
            final List<Airport> list = new ArrayList<>();
            final PreparedStatement statement = connection.prepareStatement(query);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(createAirport(resultSet));
            }
            return list;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_ALL_AIRPORTS.get();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    private Airport createAirport(final ResultSet resultSet) throws SQLException {

        return new Airport(resultSet.getInt("apid"),
                resultSet.getString("apname"),
                resultSet.getInt("apnum"),
                resultSet.getString("aptype"),
                resultSet.getString("address"),
                resultSet.getInt("terminalcount"));
    }

    private void fillPreparedStatement(final Airport airport, final PreparedStatement statement) throws SQLException {
        statement.setString(StatementParameter.AIRPORT_NAME.get(), airport.getApName());
        statement.setInt(StatementParameter.AIRPORT_NUM.get(), airport.getApNum());
        statement.setString(StatementParameter.AIRPORT_TYPE.get(), airport.getApType());
        statement.setString(StatementParameter.AIRPORT_ADDRESSES.get(), airport.getAddress());
        statement.setInt(StatementParameter.AIRPORT_TERMINAL.get(), airport.getTerminalCount());
    }
}
