package com.ra.airport.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Plane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlaneDao implements AirPortDao<Plane> {

    private final transient JdbcTemplate jdbcTemplate;

    private static final String INSERT_PLANE_SQL = "INSERT INTO plane "
            + "(owner, model, type, platenumber) "
            + " VALUES(?,?,?,?)";
    private static final String UPDATE_PLANE_SQL = "UPDATE plane "
            + "SET owner = ?, model = ?, type = ?, platenumber = ?"
            + "WHERE planeId = ?";

    private static final Logger LOGGER = LogManager.getLogger(PlaneDao.class);

    @Autowired
    public PlaneDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Plane create(final Plane plane) throws AirPortDaoException {
        try {
            jdbcTemplate.update(INSERT_PLANE_SQL, statement -> fillPreparedStatement(plane, statement));
            final Integer planeId = jdbcTemplate.queryForObject("SELECT SCOPE_IDENTITY()", Integer.class);
            return getById(planeId).orElse(plane);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.toString(), e);
            throw new AirPortDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.get(), e);
        }
    }

    @Override
    public Plane update(final Plane plane) throws AirPortDaoException {
        try {
            jdbcTemplate.update(UPDATE_PLANE_SQL, ps -> fillPreparedStatement(plane, ps));
            return getById(plane.getPlaneId()).orElse(plane);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e)  {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_PLANE_WITH_ID.get() + plane.getPlaneId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }

    }

    @Override
    public boolean delete(final Plane plane) throws AirPortDaoException {

        try {
            final int numOfDeletedRow = jdbcTemplate.update("DELETE FROM plane WHERE planeId=?", plane.getPlaneId());
            return numOfDeletedRow > 0;
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e)  {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_PLANE_WITH_ID.get() + plane.getPlaneId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }

    }

    @Override
    public List<Plane> getAll() throws AirPortDaoException {

        try {
            final BeanPropertyRowMapper<Plane> rowMapper = BeanPropertyRowMapper.newInstance(Plane.class);
            return jdbcTemplate.query("SELECT*FROM plane", rowMapper);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_PLANES.get();
            LOGGER.error(message, e);
            throw new AirPortDaoException(message, e);
        }

    }

    @Override
    public Optional<Plane> getById(final Integer planeId) throws AirPortDaoException {
        if (planeId == null) {
            throw new AirPortDaoException(ExceptionMessage.PLANE_ID_CANNOT_BE_NULL.get());
        }
        try {
            final BeanPropertyRowMapper<Plane> rowMapper = BeanPropertyRowMapper.newInstance(Plane.class);
            final Object[] queryParams = {planeId};
            final Plane plane = jdbcTemplate.queryForObject("SELECT * FROM plane WHERE planeId=?", queryParams, rowMapper);
            return Optional.ofNullable(plane);
        } catch (EmptyResultDataAccessException | BadSqlGrammarException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_PLANE_WITH_ID.get() + planeId;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }

    }

    private void fillPreparedStatement(final Plane plane, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(StatementParameter.PLANE_OWNER.get(), plane.getOwner());
        preparedStatement.setString(StatementParameter.PLANE_MODEL.get(), plane.getModel());
        preparedStatement.setString(StatementParameter.PLANE_TYPE.get(), plane.getType());
        preparedStatement.setInt(StatementParameter.PLANE_PLATE_NUMBER.get(), plane.getPlateNumber());
        if (plane.getPlaneId() != null) {
            preparedStatement.setInt(StatementParameter.PLANE_ID.get(), plane.getPlaneId());
        }
    }
}
