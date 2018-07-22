package com.ra.airport.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ra.airport.entity.Flight;
import org.springframework.jdbc.core.RowMapper;

public class FlightRowMapper implements RowMapper<Flight> {

    private static final String FLIGHT_ID = "id";
    private static final String NAME = "name";
    private static final String CARRIER = "carrier";
    private static final String DURATION = "duration";
    private static final String MEAL_ON = "meal_on";
    private static final String FARE = "fare";
    private static final String DEPARTURE_DATE = "departure_date";
    private static final String ARRIVAL_DATE = "arrival_date";

    /**
     * Map {@link ResultSet} to {@link Flight} instance.
     *
     * @param resultSet source {@link ResultSet}
     * @return {@link Flight} with filled fields
     * @throws SQLException standard SQL exception
     */
    @Override
    public Flight mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Flight flight = new Flight();
        flight.setIdentifier(resultSet.getInt(FLIGHT_ID));
        flight.setName(resultSet.getString(NAME));
        flight.setCarrier(resultSet.getString(CARRIER));
        flight.setDuration(resultSet.getTime(DURATION).toLocalTime());
        flight.setMealOn(resultSet.getBoolean(MEAL_ON));
        flight.setFare(resultSet.getDouble(FARE));
        flight.setDepartureDate(resultSet.getTimestamp(DEPARTURE_DATE).toLocalDateTime());
        flight.setArrivalDate(resultSet.getTimestamp(ARRIVAL_DATE).toLocalDateTime());

        return flight;
    }



}
