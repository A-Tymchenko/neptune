package com.ra.courses.airport.dao.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.courses.airport.dao.AirportDAO;
import com.ra.courses.airport.entity.Airport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AirportDAOImpl implements AirportDAO {

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
    public void addAirport(final Airport airport) {
        final String query = "INSERT INTO "
                + "Airport(apid, apname, apnum, aptype, addresses, terminalcount) "
                + "VALUES('" + airport.getApid() + "',"
                + "'" + airport.getApname() + "',"
                + "'" + airport.getApnum() + "',"
                + "'" + airport.getAptype() + "',"
                + "'" + airport.getAddresses() + "',"
                + "'" + airport.getTerminalcount() + "')";
        try {
            conn.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void updateAirport(final Airport airport) {
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
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void deleteAirport(final Airport airport) {
        final String query = "DELETE FROM Airport "
                + "WHERE apid = '" + airport.getApid() + "';";
        try {
            conn.getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Optional<Airport> getAirport(final String apid) {
        final String query = "Select * From Airport Where apid =" + apid;
        Optional<Airport> optionalAirport;
        try {
            final ResultSet res = conn.getConnection().createStatement().executeQuery(query);
            if (res.next()) {
                optionalAirport = Optional.of(createAirport(res));
                return optionalAirport;
            }
        } catch (SQLException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Airport> getAirports() {
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

        return new Airport(res.getString(AIRPORT_ID),
                res.getString(AIRPORT_NAME),
                res.getInt(AIRPORT_NUM),
                res.getString(AIRPORT_TYPE),
                res.getString(AIRPORT_ADDRESSES),
                res.getInt(AIRPORT_TERMINAL));
    }
}
