package com.ra.airport.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.airport.dao.AirPortDao;
import com.ra.airport.dao.exception.AirPortDaoException;
import com.ra.airport.dao.exception.ExceptionMessage;
import com.ra.airport.entity.Plane;
import com.ra.airport.factory.ConnectionFactory;
import com.ra.airport.mapper.PlaneRowMapper;
import com.ra.airport.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlaneDao implements AirPortDao<Plane> {

    private final transient ConnectionFactory connectionFactory;

    private static final String INSERT_PLANE_SQL = "INSERT INTO plane "
            + "(owner, type, model, platenumber) "
            + " VALUES(?,?,?,?)";
    private static final String UPDATE_PLANE_SQL = "UPDATE plane "
            + "SET owner = ?, model = ?, type = ?, platenumber = ?"
            + "WHERE id = ?";

    private static final Logger LOGGER = LogManager.getLogger(FlightDao.class);

    public PlaneDao(final ConnectionFactory  connectionFactory) {
        this.connectionFactory = connectionFactory; }

    @Override
    public Plane create(Plane plane) throws AirPortDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PLANE_SQL);
            fillPreparedStatement(plane, preparedStatement);
            preparedStatement.executeUpdate();
            final ResultSet generatedIdRS = connection.prepareStatement("SELECT SCOPE_IDENTITY()").executeQuery();
            Integer planeId = null;
            if (generatedIdRS.next()) {
                planeId = generatedIdRS.getInt(1);
            }
            plane = getById(planeId).get();
        } catch (SQLException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.toString(), e);
            throw new AirPortDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.get(), e);
        }
        LOGGER.debug("Plane with id was created {}", plane.getIdentifier());
        return plane;
    }

    @Override
    public Plane update(Plane plane) throws AirPortDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PLANE_SQL);
            fillPreparedStatement(plane, preparedStatement);
            preparedStatement.executeUpdate();
            plane = getById(plane.getIdentifier()).get();
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_PLANE_WITH_ID.get() + plane.getIdentifier();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        LOGGER.debug("Plane with id was updated {}", plane.getIdentifier());
        return plane;
    }

    @Override
    public boolean delete(final Plane plane) throws AirPortDaoException {
        boolean result;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM plane WHERE id = ?");
            preparedStatement.setInt(1, plane.getIdentifier());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_PLANE_WITH_ID.get() + plane.getIdentifier();
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return result;
    }

    @Override
    public List<Plane> getAll() throws AirPortDaoException {
        final List<Plane> planes = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM plane");
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                createPlane(planes, resultSet);
            }
        } catch (SQLException e) {
            final String message = ExceptionMessage.FAILED_TO_GET_ALL_PLANES.get();
            LOGGER.error(message, e);
            throw new AirPortDaoException(message, e);
        }
        return planes;
    }

    @Override
    public Optional<Plane> getById(final Integer planeId) throws AirPortDaoException {
        if (planeId == null) {
            throw new AirPortDaoException(ExceptionMessage.PLANE_ID_CANNOT_BE_NULL.get());
        }
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM plane WHERE id = ?");
                    preparedStatement.setInt(1, planeId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Plane plane = new PlaneRowMapper().mapRow(resultSet, new Plane());
                return Optional.of(plane);
            }
        } catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_GET_PLANE_WITH_ID.get() + planeId;
            LOGGER.error(errorMessage, e);
            throw new AirPortDaoException(errorMessage, e);
        }
        return Optional.empty();
    }

    private void createPlane(final List<Plane> planes, final ResultSet resultSet) throws SQLException {
        Plane plane = new Plane();
        final RowMapper<Plane> rowMapper = new PlaneRowMapper();
        plane = rowMapper.mapRow(resultSet, plane);
        planes.add(plane);
    }

    private void fillPreparedStatement(final Plane plane, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(StatementParameter.PLANE_OWNER.get(), plane.getOwner());
        preparedStatement.setString(StatementParameter.PLANE_MODEL.get(), plane.getModel());
        preparedStatement.setString(StatementParameter.PLANE_TYPE.get(), plane.getType());
        preparedStatement.setInt(StatementParameter.PLATE_NUMBER.get(), plane.getPlateNumber());
        if (plane.getIdentifier() != null) {
            preparedStatement.setInt(StatementParameter.PLANE_ID.get(), plane.getIdentifier());
        }
    }
}
