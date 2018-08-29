package com.ra.airport.service;

import java.util.ArrayList;
import java.util.List;

import com.ra.airport.dto.TicketDTO;
import com.ra.airport.entity.Ticket;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.TicketDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Ticket} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Component
public class TicketService implements AirPortService<TicketDTO> {

    private final transient TicketDao ticketDao;

    @Autowired
    public TicketService(final TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public TicketDTO create(final TicketDTO ticketDTO) throws AirPortDaoException {
        var ticket = new Ticket();
        BeanUtils.copyProperties(ticket, ticketDTO);
        ticket = ticketDao.create(ticket);
        ticketDTO.setTicketId(ticket.getTicketId());
        return ticketDTO;
    }

    @Override
    public TicketDTO update(final TicketDTO ticketDTO) throws AirPortDaoException {
        var ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);
        ticketDao.create(ticket);
        return ticketDTO;
    }

    @Override
    public boolean delete(final TicketDTO ticketDTO) throws AirPortDaoException {
        var ticket = new Ticket();
        ticket.setTicketId(ticketDTO.getTicketId());
        return ticketDao.delete(ticket);
    }

    @Override
    public List<TicketDTO> getAll() throws AirPortDaoException {
        var result = new ArrayList<TicketDTO>();
        for (Ticket ticket : ticketDao.getAll()) {
            result.add(createTicketDto(ticket));
        }
        return result;
    }

    private TicketDTO createTicketDto(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);
        return ticketDTO;
    }
}
