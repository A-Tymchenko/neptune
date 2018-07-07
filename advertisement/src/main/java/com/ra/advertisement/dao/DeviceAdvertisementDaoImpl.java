package com.ra.advertisement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.model.entities.Device;

public final class DeviceAdvertisementDaoImpl implements AdvertisementDao<Device> {

    private final transient ConnectionFactory connectionFactory;
    private static final String UPDATE_DEVICE = "update DEVICES set NAME = ?, MODEL= ?, DEVICE_TYPE = ? "
            + "where DEV_ID = ?";
    private static final String CREATE_DEVICE = "INSERT INTO DEVICES (NAME, MODEL, DEVICE_TYPE) VALUES(?,?,?)";
    private static final String SELECT_DEV_BY_ID = "SELECT * FROM DEVICES WHERE DEV_ID=?";
    private static final String DELETE_DEV_BY_ID = "DELETE FROM DEVICES WHERE DEV_ID=?";
    private static final String GET_ALL_DEVICES = "SELECT * FROM DEVICES";
    private static final Integer NAME = 1;
    private static final Integer MODEL = 2;
    private static final Integer DEVICE_TYPE = 3;
    private static final Integer DEV_ID = 4;
    private static final Logger LOGGER = Logger.getLogger(DeviceAdvertisementDaoImpl.class.getName());

    public DeviceAdvertisementDaoImpl(final ConnectionFactory connFactory) {
        this.connectionFactory = connFactory;
    }

    /**
     * Method extracts a device from resultSet.
     *
     * @param resultSet resultSet recieved from a Data Base
     * @return device with the filled fields
     */
    private Device getDeviceFromResultSet(final ResultSet resultSet) throws SQLException {
        final Device device = new Device();
        device.setDevId(resultSet.getLong("DEV_ID"));
        device.setName(resultSet.getString("NAME"));
        device.setModel(resultSet.getString("MODEL"));
        device.setDeviceType(resultSet.getString("DEVICE_TYPE"));
        return device;
    }

    /**
     * Method sets Statement values.
     *
     * @param pstm   to save
     * @param device to save
     */
    private void setStatementValues(final PreparedStatement pstm, final Device device) throws SQLException {
        pstm.setString(NAME, device.getName());
        pstm.setString(MODEL, device.getModel());
        pstm.setString(DEVICE_TYPE, device.getDeviceType());
        pstm.setLong(DEV_ID, device.getDevId());
    }

    /**
     * Method adds new Device to the Data Base.
     *
     * @param device DEvice to save
     * @returnto count of added rows
     */
    @Override
    public Integer create(final Device device) throws DaoException {

        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(CREATE_DEVICE);
            pstm.setString(NAME, device.getName());
            pstm.setString(MODEL, device.getModel());
            pstm.setString(DEVICE_TYPE, device.getDeviceType());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the create  method check if the Devices table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }

    }


    /**
     * Method returns Device from Data Base by id.
     *
     * @param devId Device's id
     * @return object Device in Optional
     */
    @Override
    public Optional<Device> getById(final Long devId) throws DaoException {
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(SELECT_DEV_BY_ID);
            pstm.setLong(1, devId);
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getDeviceFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method check if the Device table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes  the object from Data Base by its id.
     *
     * @param devId Device's Id
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Long devId) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(DELETE_DEV_BY_ID);
            pstm.setLong(1, devId);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the delete method check if the Device table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);

        }
    }

    /**
     * Update Device to Data Base.
     *
     * @param device to save
     * @return count of updated rows
     */
    @Override
    public Integer update(final Device device) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(UPDATE_DEVICE);
            setStatementValues(pstm, device);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method check if the Device table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);

        }
    }

    /**
     * Method gets all devices from Data Base.
     *
     * @return list of devices or empty otherwise
     */
    @Override
    public List<Device> getAll() throws DaoException {
        final List<Device> deviceList = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(GET_ALL_DEVICES);
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Device device = getDeviceFromResultSet(resultSet);
                deviceList.add(device);
            }
        } catch (SQLException ex) {
            final String message = "Check if the Device table exists";
            LOGGER.log(Level.WARNING, "Check if the Device table exists", ex);
            throw new DaoException(message, ex);
        }

        return deviceList;
    }

}
