package com.ra.airport.dao.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.entity.Airport;

import com.ra.airport.factory.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirportDAOImpl implements AirPortDao<Airport> {

    private final transient ConnectionFactory conn;

    private static final int AIRPORT_ID = 1;
    private static final int AIRPORT_NAME = 2;
    private static final int AIRPORT_NUM = 3;
    private static final int AIRPORT_TYPE = 4;
    private static final int AIRPORT_ADDRESSES = 5;
    private static final int AIRPORT_TERMINAL = 6;
    private static final Logger LOGGER = LogManager.getLogger(AirportDAOImpl.class);

    public AirportDAOImpl(final ConnectionFactory conn) {
        this.conn = conn;
    }

    @Override
    public Airport create(Airport airport) throws AirPortDaoException {
        final String query = "INSERT INTO "
                + "Airport(apname, apnum, aptype, addresses, terminalcount) "
                + "VALUES('" + airport.getApname() + "',"
                + "'" + airport.getApnum() + "',"
                + "'" + airport.getAptype() + "',"
                + "'" + airport.getAddresses() + "',"
                + "'" + airport.getTerminalcount() + "')";
        try {
            final Statement statement = conn.getConnection().createStatement();
            statement.executeUpdate(query);
            final ResultSet resultSet = statement.executeQuery("Select LAST_INSERT_ID() from Airport");
            if (resultSet.next()) {
                airport = getById(resultSet.getInt(AIRPORT_ID)).get();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return airport;
    }

    @Override
    public Airport update(Airport airport) throws AirPortDaoException {
        final String query = "UPDATE Airport "
                + "SET "
                + "apname = '" + airport.getApname() + "',"
                + " apnum = '" + airport.getApnum() + "',"
                + " aptype = '" + airport.getAptype() + "',"
                + " addresses = '" + airport.getAddresses() + "',"
                + " terminalcount = '" + airport.getTerminalcount() + "'"
                + " WHERE apid = '" + airport.getApid() + "';";
        try {
            conn.getConnection().createStatement().executeUpdate(query);
            airport = getById(airport.getApid()).get();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return airport;
    }

    @Override
    public boolean delete(final Airport airport) throws AirPortDaoException {
        boolean result;
        final String query = "DELETE FROM Airport "
                + "WHERE apid = '" + airport.getApid() + "';";
        try {
            result = conn.getConnection().createStatement().execute(query);
        } catch (SQLException e) {
            final String errorMessage = "Failed to delete airport: " + airport.getApid();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return result;
    }

    @Override
    public Optional<Airport> getById(final Integer apid) throws AirPortDaoException {
        final String query = "Select * From Airport Where apid =" + apid;
        Optional<Airport> optionalAirport;
        try {
            final ResultSet res = conn.getConnection().createStatement().executeQuery(query);
            if (res.next()) {
                optionalAirport = Optional.of(createAirport(res));
                return optionalAirport;
            }
        } catch (SQLException | IOException e) {
            final String errorMessage = "Failed t oget airport by id: " + apid;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Airport> getAll() {
        final List<Airport> list = new ArrayList<Airport>();
        try {
            final ResultSet res = conn.getConnection().createStatement().executeQuery("Select * From Airport");
            while (res.next()) {
                list.add(createAirport(res));
            }
            return list;
        } catch (SQLException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        return list;
    }

    private Airport createAirport(final ResultSet res) throws IOException, SQLException {

        return new Airport(res.getInt(AIRPORT_ID),
                res.getString(AIRPORT_NAME),
                res.getInt(AIRPORT_NUM),
                res.getString(AIRPORT_TYPE),
                res.getString(AIRPORT_ADDRESSES),
                res.getInt(AIRPORT_TERMINAL));
    }
}
