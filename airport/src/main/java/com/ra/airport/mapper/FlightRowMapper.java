package com.ra.airport.mapper;

import com.ra.airport.entity.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightRowMapper implements RowMapper<Flight> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CARRIER = "carrier";
    private static final String DURATION = "duration";
    private static final String MEAL = "meal";
    private static final String FARE = "fare";
    private static final String DEPARTURE_DATE = "departure_date";
    private static final String ARRIVAL_DATE = "arrival_date";

    /**
     * Map {@link ResultSet} to {@link Flight} instance.
     * @param resultSet
     * @param flight
     * @return
     * @throws SQLException
     */
    @Override
    public Flight mapRow(ResultSet resultSet, Flight flight) throws SQLException {
        flight.setId(resultSet.getInt(ID));
        flight.setName(resultSet.getString(NAME));
        flight.setCarrier(resultSet.getString(CARRIER));
        flight.setDuration(resultSet.getTime(DURATION).toLocalTime());
        flight.setMealOn(resultSet.getBoolean(MEAL));
        flight.setFare(resultSet.getDouble(FARE));
        flight.setDepartureDate(resultSet.getTimestamp(DEPARTURE_DATE).toLocalDateTime());
        flight.setArrivalDate(resultSet.getTimestamp(ARRIVAL_DATE).toLocalDateTime());

        return flight;
    }
}
