package com.ra.advertisement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ra.advertisement.entity.Device;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public final class DeviceAdvertisementDaoImpl implements AdvertisementDao<Device> {
    private final transient JdbcTemplate jdbcTemplate;
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();
    private static final String GET_DEVICE_BY_ID = "SELECT * FROM DEVICES WHERE DEV_ID=?";
    private static final Integer NAME = 1;
    private static final Integer MODEL = 2;
    private static final Integer DEVICE_TYPE = 3;
    private static final Integer DEV_ID = 4;

    public DeviceAdvertisementDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds new Device to the Data Base.
     *
     * @param device Device to save
     * @returnto new Device
     */
    @Override
    public Device create(final Device device) {
        final String createDevice = "INSERT INTO DEVICES (NAME, MODEL, DEVICE_TYPE) VALUES(?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(createDevice);
                    preparedStatementForCreateOrUpdate(preparedStatement, device);
                    return preparedStatement;
                }, keyHolder);
        final Long deviceKey = (Long) keyHolder.getKey();
        return jdbcTemplate.queryForObject(GET_DEVICE_BY_ID, BeanPropertyRowMapper.newInstance(Device.class), deviceKey);
    }

    /**
     * Method returns Device from Data Base by id.
     *
     * @param devId Device's id
     * @return object Device
     */
    @Override
    public Device getById(final Long devId) {
        return jdbcTemplate.queryForObject(GET_DEVICE_BY_ID, BeanPropertyRowMapper.newInstance(Device.class), devId);
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param device Device we want delete
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Device device) {
        final String deleteDevice = "DELETE FROM DEVICES WHERE DEV_ID=?";
        return jdbcTemplate.update(deleteDevice, device.getDevId());
    }

    /**
     * Update device to Data Base.
     *
     * @param device device to update
     * @return new Device
     */
    @Override
    public Device update(final Device device) {
        final String updateDevice = "update DEVICES set NAME = ?, MODEL= ?, DEVICE_TYPE = ? where DEV_ID = ?";
        jdbcTemplate.update(updateDevice, ps -> {
            preparedStatementForCreateOrUpdate(ps, device);
            ps.setLong(DEV_ID, device.getDevId());
        });
        return jdbcTemplate.queryForObject(GET_DEVICE_BY_ID, BeanPropertyRowMapper.newInstance(Device.class),
                device.getDevId());
    }

    /**
     * Method gets all devices from Data Base.
     *
     * @return list of all devices or empty otherwise
     */
    @Override
    public List<Device> getAll() {
        final String getAllDevices = "SELECT * FROM DEVICES";
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(getAllDevices);
        return mapListFromQueryForList(rows);
    }

    /**
     * We use this method for fill up preparedStatement.
     *
     * @param preparedStatement preparedStatement to fill up
     * @param device            device where we get fields from
     * @throws SQLException Sqlexception
     */
    public void preparedStatementForCreateOrUpdate(final PreparedStatement preparedStatement,
                                                   final Device device) throws SQLException {
        preparedStatement.setString(NAME, device.getName());
        preparedStatement.setString(MODEL, device.getModel());
        preparedStatement.setString(DEVICE_TYPE, device.getDeviceType());
    }

    /**
     * this method map listOfCollections from query to list.
     * @param rows rows
     * @return list
     */
    public List<Device> mapListFromQueryForList(final List<Map<String, Object>> rows) {
        return rows.stream().map(row -> {
            final Device device = new Device();
            device.setDevId((Long) row.get("DEV_ID"));
            device.setName((String) row.get("NAME"));
            device.setModel((String) row.get("MODEL"));
            device.setDeviceType((String) row.get("DEVICE_TYPE"));
            return device;
        }).collect(Collectors.toList());
    }
}
