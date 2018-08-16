package com.ra.airport.service;

import java.util.List;
import java.util.Optional;

import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.AirportDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirportServiceImpl implements AirPortService<Airport> {

    private final transient AirportDAOImpl airportDAO;

    @Autowired
    public AirportServiceImpl(final AirportDAOImpl airportDAO) {
        this.airportDAO = airportDAO;
    }

    @Override
    public Airport create(final Airport entity) throws AirPortDaoException {
        return airportDAO.create(entity);
    }

    @Override
    public Airport update(final Airport entity) throws AirPortDaoException {
        return airportDAO.update(entity);
    }

    @Override
    public boolean delete(final Airport entity) throws AirPortDaoException {
        return airportDAO.delete(entity);
    }

    @Override
    public Optional<Airport> getById(final int entityId) throws AirPortDaoException {
        return airportDAO.getById(entityId);
    }

    @Override
    public List<Airport> getAll() throws AirPortDaoException {
        return airportDAO.getAll();
    }
}
