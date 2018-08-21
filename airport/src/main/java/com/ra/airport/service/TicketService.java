package com.ra.airport.service;

import java.util.List;
import java.util.Optional;

import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.TicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Ticket} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Component
public class TicketService implements AirPortService<Ticket> {

    private final transient TicketDao ticketDao;

    @Autowired
    public TicketService(final TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public Ticket create(final Ticket entity) throws AirPortDaoException {
        return ticketDao.create(entity);
    }

    @Override
    public Ticket update(final Ticket entity) throws AirPortDaoException {
        return ticketDao.update(entity);
    }

    @Override
    public boolean delete(final Ticket entity) throws AirPortDaoException {
        return ticketDao.delete(entity);
    }

    @Override
    public Optional<Ticket> getById(final int entityId) throws AirPortDaoException {
        return ticketDao.getById(entityId);
    }

    @Override
    public List<Ticket> getAll() throws AirPortDaoException {
        return ticketDao.getAll();
    }
}
