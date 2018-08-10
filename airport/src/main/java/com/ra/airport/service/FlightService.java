package com.ra.airport.service;

import java.util.List;
import java.util.Optional;

import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Flight} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Service
public class FlightService implements AirPortService<Flight> {

    private final transient FlightDao flightDao;

    @Autowired
    public FlightService(final FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public Flight create(final Flight flight) throws AirPortDaoException {
        return flightDao.create(flight);
    }

    @Override
    public Flight update(final Flight flight) throws AirPortDaoException {
        return null;
    }

    @Override
    public boolean delete(final Flight flight) throws AirPortDaoException {
        return false;
    }

    @Override
    public Optional<Flight> getById(final int flightId) throws AirPortDaoException {
        return Optional.empty();
    }

    @Override
    public List getAll() throws AirPortDaoException {
        return flightDao.getAll();
    }
}
