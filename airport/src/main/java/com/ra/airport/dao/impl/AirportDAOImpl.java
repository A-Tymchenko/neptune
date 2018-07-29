package com.ra.airport.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Airport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AirportDAOImpl implements AirPortDao<Airport> {

    private static JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(AirportDAOImpl.class);

    @Autowired
    public AirportDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Airport create(final Airport airport) throws AirPortDaoException {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            final String query = "INSERT INTO Airport(apname, apnum, aptype, address, terminalcount) "
                    + "VALUES(?, ?, ?, ?, ?)";
            jdbcTemplate.update(connection -> {
                final PreparedStatement preparedStatement = connection.prepareStatement(query);
                fillPreparedStatement(preparedStatement, airport);
                return  preparedStatement;
            }, keyHolder);
            airport.setApId((Integer) keyHolder.getKey());
            return airport;
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_CREATE_NEW_AIRPORT.get() + airport.getApId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }

    }

    @Override
    public Airport update(final Airport airport) throws AirPortDaoException {
        try {
            final String query = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, address = ?, terminalcount = ?"
                    + " WHERE apid = ?";
            jdbcTemplate.update(query, statement -> {
                fillPreparedStatement(statement, airport);
                statement.setInt(StatementParameter.AIRPORT_ID.get(), airport.getApId());
            });
            return airport;
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_AIRPORT_WITH_ID.get() + airport.getApId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }

    }

    @Override
    public boolean delete(final Airport airport) throws AirPortDaoException {
        try {
            final String query = "DELETE FROM Airport "
                    + "WHERE apid = ?";
            return jdbcTemplate.update(query, statement -> statement.setInt(1, airport.getApId())) > 0;
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_AIRPORT_WITH_ID.get() + airport.getApId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    @Override
    public Optional<Airport> getById(final Integer airportId) throws AirPortDaoException {
        try {
            final String query = "Select * From Airport Where apid = ?";
            final BeanPropertyRowMapper<Airport> rowMapper = BeanPropertyRowMapper.newInstance(Airport.class);
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, rowMapper, airportId));
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_AIRPORT_WITH_ID.get() + airportId;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    @Override
    public List<Airport> getAll() throws AirPortDaoException {
        try {
            final String query = "Select * From Airport";
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Airport.class));
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_ALL_AIRPORTS.get();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }

    }

    private void fillPreparedStatement(final PreparedStatement statement, final Airport airport) throws SQLException {
        statement.setString(StatementParameter.AIRPORT_NAME.get(), airport.getApName());
        statement.setInt(StatementParameter.AIRPORT_NUM.get(), airport.getApNum());
        statement.setString(StatementParameter.AIRPORT_TYPE.get(), airport.getApType());
        statement.setString(StatementParameter.AIRPORT_ADDRESSES.get(), airport.getAddress());
        statement.setInt(StatementParameter.AIRPORT_TERMINAL.get(), airport.getTerminalCount());
    }
}
