package com.ra.advertisement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ra.advertisement.connection.ConnectionFactory;
import com.ra.advertisement.dao.exceptions.AdvertisementEnum;
import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DeviceAdvertisementDaoImpl implements AdvertisementDao<Device> {

    private final transient ConnectionFactory connectionFactory;
    private static final Integer NAME = 1;
    private static final Integer MODEL = 2;
    private static final Integer DEVICE_TYPE = 3;
    private static final Integer DEV_ID = 4;
    private static final Logger LOGGER = LogManager.getLogger(DeviceAdvertisementDaoImpl.class);

    public DeviceAdvertisementDaoImpl(final ConnectionFactory connFactory) {
        this.connectionFactory = connFactory;
    }

    /**
     * Method adds new Device to the Data Base.
     *
     * @param device Device to save
     * @return entity Device with generated id
     */
    @Override
    public Device create(final Device device) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("INSERT INTO DEVICES (NAME, MODEL, "
                    + "DEVICE_TYPE) VALUES(?,?,?)");
            setStatementValues(pstm, device);
            pstm.executeUpdate();
            final ResultSet resultSetWithKey = pstm.getGeneratedKeys();
            if (resultSetWithKey.next()) {
                return getById(resultSetWithKey.getLong(1)).get();
            }
            return device;
        } catch (SQLException ex) {
            final String message = "Trouble in the create method {}";
            LOGGER.error(message, AdvertisementEnum.DEVICES.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.DEVICES.getMessage()), ex);
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
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM DEVICES WHERE DEV_ID=?");
            pstm.setLong(1, devId);
            final ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getDeviceFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method {}";
            LOGGER.error(message, AdvertisementEnum.DEVICES.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.DEVICES.getMessage()), ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param device Device
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Device device) throws DaoException {
        if (device != null) {
            try (Connection connection = connectionFactory.getConnection()) {
                final PreparedStatement pstm = connection.prepareStatement("DELETE FROM DEVICES WHERE DEV_ID=?");
                pstm.setLong(1, device.getDevId());
                return pstm.executeUpdate();
            } catch (SQLException ex) {
                final String message = "Trouble in the delete method {}";
                LOGGER.error(message, AdvertisementEnum.DEVICES.getMessage(), ex);
                throw new DaoException(String.format(message, AdvertisementEnum.DEVICES.getMessage()), ex);
            }
        } else {
            return 0;
        }
    }

    /**
     * Update Device to Data Base.
     *
     * @param device to save
     * @return new entity Device updated
     */
    @Override
    public Device update(final Device device) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("update DEVICES set NAME = ?, MODEL= ?, "
                    + "DEVICE_TYPE = ? where DEV_ID = ?");
            setStatementValues(pstm, device);
            pstm.setLong(DEV_ID, device.getDevId());
            pstm.executeUpdate();
            return getById(device.getDevId()).get();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method {}";
            LOGGER.error(message, AdvertisementEnum.DEVICES.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.DEVICES.getMessage()), ex);
        }
    }

    /**
     * Method gets all devices from Data Base.
     *
     * @return list of devices or empty otherwise
     */
    @Override
    public List<Device> getAll() throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final List<Device> deviceList = new ArrayList<>();
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM DEVICES");
            final ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Device device = getDeviceFromResultSet(resultSet);
                deviceList.add(device);
            }
            return deviceList;
        } catch (SQLException ex) {
            final String message = "Trouble in the getAll method {}";
            LOGGER.error(message, AdvertisementEnum.DEVICES.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.DEVICES.getMessage()), ex);
        }
    }

    /**
     * Method extracts a device from resultSet.
     *
     * @param resultSet resultSet recieved from a Data Base
     * @return new Device with the filled fields
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
     * Method sets Statement values for create method.
     *
     * @param pstm   to save
     * @param device to save
     */
    private void setStatementValues(final PreparedStatement pstm, final Device device) throws SQLException {
        pstm.setString(NAME, device.getName());
        pstm.setString(MODEL, device.getModel());
        pstm.setString(DEVICE_TYPE, device.getDeviceType());
    }
}
