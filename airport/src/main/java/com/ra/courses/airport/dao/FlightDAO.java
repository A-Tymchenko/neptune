package com.ra.courses.airport.dao;

import com.ra.courses.airport.entity.Flight;

/**
 * Created by anbo06131 on 6/15/2018.
 */
public interface FlightDAO {

    Flight create();

    Flight update(Flight flight);

    boolean delete(Integer flightId);
}
