package com.ra.airport.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.airport.repository.exception.AirPortDaoException;
import org.springframework.stereotype.Component;

@Component
public class AddAirprtHandler implements ServletHandler {

    @Override
    public void post(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void get(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void delete(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }

    @Override
    public void put(final HttpServletRequest request, final HttpServletResponse response) throws AirPortDaoException {

    }
}
