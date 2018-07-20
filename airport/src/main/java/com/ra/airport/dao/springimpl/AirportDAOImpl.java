package com.ra.airport.dao.springimpl;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.impl.StatementParameter;
import com.ra.airport.entity.Airport;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AirportDAOImpl implements AirPortDao<Airport> {

    private final JdbcTemplate jdbcTemplate;

    public AirportDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Airport create(final Airport airport) {
        final String query = "INSERT INTO Airport(apname, apnum, aptype, address, terminalcount) "
                + "VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, statement -> fillPreparedStatement(statement, airport));
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("Select LAST_INSERT_ID() from Airport");
        if(sqlRowSet.next()) {
            return this.getById(sqlRowSet.getInt(1)).get();
        }
        return airport;
    }

    @Override
    public Airport update(Airport airport) {
        final String query = "UPDATE Airport SET apname = ?, apnum = ?, aptype = ?, address = ?, terminalcount = ?"
                + " WHERE apid = ?";
        jdbcTemplate.update(query, statement -> {
            fillPreparedStatement(statement, airport);
            statement.setInt(StatementParameter.AIRPORT_ID.get(), airport.getApId());
        });
        return this.getById(airport.getApId()).get();
    }

    @Override
    public boolean delete(Airport airport) {
        final String query = "DELETE FROM Airport "
                + "WHERE apid = ?";
        return jdbcTemplate.update(query, statement -> statement.setInt(1, airport.getApId())) > 0;
    }

    @Override
    public Optional<Airport> getById(Integer entityId) {
        final String query = "Select * From Airport Where apid = ?";
        return Optional.of(jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Airport.class), entityId));
    }

    @Override
    public List<Airport> getAll() {
        final String query = "Select * From Airport";
        return jdbcTemplate.query(query,BeanPropertyRowMapper.newInstance(Airport.class));
    }

    private void fillPreparedStatement(final PreparedStatement statement, final Airport airport) throws SQLException {
        statement.setString(StatementParameter.AIRPORT_NAME.get(), airport.getApName());
        statement.setInt(StatementParameter.AIRPORT_NUM.get(), airport.getApNum());
        statement.setString(StatementParameter.AIRPORT_TYPE.get(), airport.getApType());
        statement.setString(StatementParameter.AIRPORT_ADDRESSES.get(), airport.getAddress());
        statement.setInt(StatementParameter.AIRPORT_TERMINAL.get(), airport.getTerminalCount());
    }
}
