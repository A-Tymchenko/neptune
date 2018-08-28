package com.ra.airport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dto.AirportDTO;
import com.ra.airport.entity.Airport;
import com.ra.airport.repository.exception.AirPortDaoException;
import com.ra.airport.repository.impl.AirportDAOImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirportServiceImpl implements AirPortService<AirportDTO> {

    private final transient AirportDAOImpl airportDAO;

    @Autowired
    public AirportServiceImpl(final AirportDAOImpl airportDAO) {
        this.airportDAO = airportDAO;
    }

    @Override
    public AirportDTO create(final AirportDTO airportDTO) throws AirPortDaoException {
        var airport = new Airport();
        BeanUtils.copyProperties(airportDTO, airport);
        airport = airportDAO.create(airport);
        airportDTO.setApId(airport.getApId());
        return airportDTO;
    }

    @Override
    public AirportDTO update(final AirportDTO airportDTO) throws AirPortDaoException {
        var airport = new Airport();
        BeanUtils.copyProperties(airportDTO, airport);
        airportDAO.create(airport);
        return airportDTO;
    }

    @Override
    public boolean delete(final AirportDTO airportDTO) throws AirPortDaoException {
        var airport = new Airport();
        airport.setApId(airportDTO.getApId());
        return airportDAO.delete(airport);
    }

    @Override
    public List<AirportDTO> getAll() throws AirPortDaoException {
        var result = new ArrayList<AirportDTO>();
        for (Airport airport : airportDAO.getAll()) {
            result.add(createAirportDto(airport));
        }
        return result;
    }

    private AirportDTO createAirportDto(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();
        BeanUtils.copyProperties(airport, airportDTO);
        return airportDTO;
    }
}
