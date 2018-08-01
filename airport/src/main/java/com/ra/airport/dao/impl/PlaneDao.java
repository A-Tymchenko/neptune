package com.ra.airport.dao.impl;

import java.sql.Connection;
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Plane create(final Plane plane) throws AirPortDaoException {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> createPreparedStatement(plane, connection, INSERT_PLANE_SQL), keyHolder);
            plane.setPlaneId((Integer) (keyHolder.getKey()));
            return plane;
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.toString(), e);
            throw new AirPortDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.get(), e);
        }
    }

    @Override
    public Plane update(final Plane plane) throws AirPortDaoException {
        try {
            jdbcTemplate.update(connection -> {
                final PreparedStatement preparedStatement = createPreparedStatement(plane, connection, UPDATE_PLANE_SQL);
                preparedStatement.setInt(StatementParameter.PLANE_ID.get(), plane.getPlaneId());
                return preparedStatement;
            });
            return plane;
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_PLANE_WITH_ID.get() + plane.getPlaneId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    @Override
    public boolean delete(final Plane plane) throws AirPortDaoException {
        try {
            final int numOfDeletedRow = jdbcTemplate.update("DELETE FROM plane WHERE planeId = ?", plane.getPlaneId());
            return numOfDeletedRow > 0;
        } catch (DataAccessException e)  {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_PLANE_WITH_ID.get() + plane.getPlaneId();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    @Override
    public List<Plane> getAll() throws AirPortDaoException {
        try {
            final BeanPropertyRowMapper<Plane> rowMapper = BeanPropertyRowMapper.newInstance(Plane.class);
            return jdbcTemplate.query("SELECT * FROM plane", rowMapper);
        } catch (DataAccessException e) {
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
            final Plane plane = jdbcTemplate.queryForObject("SELECT * FROM plane WHERE planeId = ?", rowMapper, planeId);
            return Optional.ofNullable(plane);
        } catch (DataAccessException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_PLANE_WITH_ID.get() + planeId;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
    }

    private PreparedStatement createPreparedStatement(final Plane plane, final Connection connection, final String sql)
            throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(StatementParameter.PLANE_OWNER.get(), plane.getOwner());
        preparedStatement.setString(StatementParameter.PLANE_MODEL.get(), plane.getModel());
        preparedStatement.setString(StatementParameter.PLANE_TYPE.get(), plane.getType());
        preparedStatement.setInt(StatementParameter.PLANE_PLATE_NUMBER.get(), plane.getPlateNumber());
        return preparedStatement;
    }
}

