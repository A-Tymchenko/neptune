package com.ra.airport.service;

import java.util.List;
import java.util.Optional;

import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Flight} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Service
public class FlightService implements AirPortService {

    private FlightDao flightDao;

    @Autowired
    public FlightService(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public Object create(Object entity) throws AirPortDaoException {
        return null;
    }

    @Override
    public Object update(Object entity) throws AirPortDaoException {
        return null;
    }

    @Override
    public boolean delete(Object entity) throws AirPortDaoException {
        return false;
    }

    @Override
    public Optional getById(int entityId) throws AirPortDaoException {
        return Optional.empty();
    }

    @Override
    public List getAll() throws AirPortDaoException {
        return flightDao.getAll();
    }
}
