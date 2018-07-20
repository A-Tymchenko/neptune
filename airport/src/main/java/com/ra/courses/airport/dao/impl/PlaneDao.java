package com.ra.courses.airport.dao.impl;
//create Logger

import java.sql.Connection;

import com.ra.courses.airport.dao.impl.RowMapper;
import com.ra.courses.airport.dao.impl.PlaneRowMapper;
import com.ra.courses.airport.entity.Plane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlaneDao implements PlaneDaoInterface<Plane> {

    private final transient ConnectionFactory connectionFactory;

    private static final Integer ID=1;
    private static final Integer OWNER=2;
    private static final Integer MODEL=3;
    private static final Integer TYPE=4;
    private static final Integer PLATENUMBER=5;


    private static final String INSERT_PLANE_SQL = "INSERT INTO plane "
                         +"(owner, type, model, platenumber) "
                         +" VALUES(?,?,?,?)";
    private static final String UPDATE_PLANE_SQL = "UPDATE plane "
                         +"SET owner = ?, model = ?, type = ?, platenumber = ?"
                         +"WHERE id = ?";
    private static final String GET_PLANE_BY_ID = "SELECT * FROM plane WHERE id = ?";
    private static final String DELETE_PLANE = "DELETE FROM plane WHERE id = ?";
    private static final String GET_ALL_PLANES = "SELECT * FROM plane";
    private static final String GET_PLANE_ID = "SELECT SCOPE_IDENTITY()";

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaneDao.class);


    public PlaneDao(ConnectionFactory connectionFactory){this.connectionFactory=connectionFactory;}




    public Plane create (Plane plane) throws PlaneDaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PLANE_SQL);
            fillPreparedStatement(plane,preparedStatement);
            preparedStatement.executeUpdate();
            final ResultSet generatedIdRS = connection.prepareStatement(GET_PLANE_ID).executeQuery();
            Integer planeId = null;
            if (generatedIdRS.next()) {
                               planeId = generatedIdRS.getInt(1);
                            }
                        plane = getById(planeId).get();
                    } catch (SQLException e) {
                        LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.toString(), e);
                        throw new PlaneDaoException(ExceptionMessage.FAILED_TO_CREATE_NEW_PLANE.get(), e);
                    }
                LOGGER.debug("Plane with id was created {}", plane.getId());
                return plane;
            }

    public Plane update(Plane plane) throws PlaneDaoException {

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PLANE_SQL);
            fillPreparedStatement(plane, preparedStatement);
            preparedStatement.executeUpdate();
            plane = getById(plane.getId()).get();
        }catch (SQLException e) {
            final String errorMessage = ExceptionMessage.FAILED_TO_UPDATE_PLANE_WITH_ID.get() + plane.getId();
            LOGGER.error(errorMessage, e);
            throw new PlaneDaoException(errorMessage, e);
            }
                LOGGER.debug("Plane with id was updated {}", plane.getId());
                return plane;
            }


    public boolean delete(final Plane plane) throws PlaneDaoException {
        boolean result;
                try (Connection connection = connectionFactory.getConnection()) {
                        final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PLANE);
                        preparedStatement.setInt(1, plane.getId());
                        result = preparedStatement.executeUpdate() > 0;
                    } catch (SQLException e) {
                        final String errorMessage = ExceptionMessage.FAILED_TO_DELETE_PLANE_WITH_ID.get() + plane.getId();
                        LOGGER.error(errorMessage, e);
                        throw new PlaneDaoException(errorMessage, e);
                    }
                return result;
            }

    public Optional<Plane> getById(final Integer idPlane) throws PlaneDaoException {
        if (idPlane == null) {
                        throw new PlaneDaoException(ExceptionMessage.PLANE_ID_CANNOT_BE_NULL.get());
        }
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_PLANE_BY_ID);
                        preparedStatement.setInt(1, idPlane);
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            final Plane plane = new PlaneRowMapper().mapRow(resultSet, new Plane());
                            return Optional.of(plane);
                            }
                    } catch (SQLException e) {
                        final String errorMessage = ExceptionMessage.FAILED_TO_GET_PLANE_WITH_ID.get() + idPlane;
                        LOGGER.error(errorMessage, e);
                        throw new PlaneDaoException(errorMessage, e);
                    }
                return Optional.empty();
            }



    private Plane getPlaneFromResultSet(final ResultSet resultSet) throws SQLException {
        final Plane plane=new Plane();
        plane.setId(resultSet.getInt("ID"));
        plane.setOwner(resultSet.getString("OWNER"));
        plane.setModel(resultSet.getString("MODEL"));
        plane.setType(resultSet.getString("TYPE"));
        plane.setPlatenumber(resultSet.getInt("PLATENUMBER"));
        return  plane;
    }



    public List<Plane> getAll() throws PlaneDaoException {
                final List<Plane> planes = new ArrayList<>();
                try (Connection connection = connectionFactory.getConnection()) {
                        final PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PLANES);
                        final ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                                createPlane(planes, resultSet);
                            }
                    } catch (SQLException e) {
                        final String message = ExceptionMessage.FAILED_TO_GET_ALL_PLANES.get();
                        LOGGER.error(message, e);
                        throw new PlaneDaoException(message, e);
                    }
                return planes;
            }

    private void createPlane(final List<Plane> planes, final ResultSet resultSet) throws SQLException {
                Plane plane = new Plane();
                final RowMapper<Plane> rowMapper = new PlaneRowMapper();
                plane = rowMapper.mapRow(resultSet, plane);
                planes.add(plane);
            }




    private void fillPreparedStatement(final Plane plane, final PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(OWNER, plane.getOwner());
                preparedStatement.setString(MODEL, plane.getModel());
                preparedStatement.setString(TYPE, plane.getType());
                preparedStatement.setInt(PLATENUMBER, plane.getPlatenumber());
                if (plane.getId()!= null) {
                        preparedStatement.setInt(ID, plane.getId());
                    }
            }





    }











