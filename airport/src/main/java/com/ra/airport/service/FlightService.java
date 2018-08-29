package com.ra.airport.service;

import java.util.ArrayList;
import java.util.List;

import com.ra.airport.dto.FlightDto;
import com.ra.airport.entity.Flight;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.FlightDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides methods for CRUD operations with {@link com.ra.airport.entity.Flight} entity.
 * Using {@link com.ra.airport.repository.AirPortDao}.
 */
@Service
public class FlightService implements AirPortService<FlightDto> {

    private final transient FlightDao flightDao;

    @Autowired
    public FlightService(final FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public FlightDto create(final FlightDto flightDTO) throws AirPortDaoException {
        var flight = new Flight();
        BeanUtils.copyProperties(flightDTO, flight);
        flight = flightDao.create(flight);
        flightDTO.setFlId(flight.getFlId());
        return flightDTO;
    }

    @Override
    public FlightDto update(final FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        flightDao.update(flight);
        return flightDto;
    }

    @Override
    public boolean delete(final FlightDto flightDto) throws AirPortDaoException {
        final var flight = new Flight();
        flight.setFlId(flightDto.getFlId());
        return flightDao.delete(flight);
    }

    @Override
    public List getAll() throws AirPortDaoException {
        final var result = new ArrayList<FlightDto>();
        for (final Flight flight : flightDao.getAll()) {
            result.add(createFlightDto(flight));
        }
        return result;
    }

    private FlightDto createFlightDto(final Flight flight) {
        final FlightDto flightDto = new FlightDto();
        BeanUtils.copyProperties(flight, flightDto);
        return flightDto;
    }
}
