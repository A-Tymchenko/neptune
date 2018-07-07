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
import com.ra.advertisement.model.entities.Advertisement;

public final class AdvertisementAdvertisementDaoImpl implements AdvertisementDao<Advertisement> {
    private final transient ConnectionFactory connectionFactory;
    private static final String UPDATE_ADVERT = "update ADVERTISEMENT set TITLE = ?, CONTEXT= ?, IMAGE_URL = ?, "
            + "LANGUAGE = ? where AD_ID = ?";
    private static final String CREATE_ADVERT = "INSERT INTO ADVERTISEMENT (TITLE, CONTEXT, IMAGE_URL, LANGUAGE, PROV_ID)"
            + "VALUES(?,?,?,?,?)";
    private static final String SELECT_ADV_BY_ID = "SELECT * FROM ADVERTISEMENT WHERE AD_ID=?";
    private static final String DELETE_ADV_BY_ID = "DELETE FROM ADVERTISEMENT WHERE AD_ID=?";
    private static final String GET_ALL_ADVERT = "SELECT * FROM ADVERTISEMENT";
    private static final Integer TITLE = 1;
    private static final Integer CONTEXT = 2;
    private static final Integer IMAGE_URL = 3;
    private static final Integer LANGUAGE = 4;
    private static final Integer PROV_ID = 5;
    private static final Integer AD_ID = 5;
    private static final Logger LOGGER = Logger.getLogger(AdvertisementAdvertisementDaoImpl.class.getName());

    public AdvertisementAdvertisementDaoImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Method extracts a advertisement from resultSet.
     *
     * @param resultSet resultSet recieved from a Data Base
     * @return advertisement with the filled fields
     */
    private Advertisement getAdvertisementFromResultSet(final ResultSet resultSet) throws SQLException {
        final Advertisement advertisement = new Advertisement();
        advertisement.setAdId(resultSet.getLong("AD_ID"));
        advertisement.setTitle(resultSet.getString("TITLE"));
        advertisement.setContext(resultSet.getString("CONTEXT"));
        advertisement.setImageUrl(resultSet.getString("IMAGE_URL"));
        advertisement.setLanguage(resultSet.getString("LANGUAGE"));
        advertisement.setProvId(resultSet.getLong("PROV_ID"));
        return advertisement;
    }

    /**
     * Method sets Statement values.
     *
     * @param pstm          to save
     * @param advertisement to save
     */
    private void setStatementValues(final PreparedStatement pstm, final Advertisement advertisement) throws SQLException {
        pstm.setString(TITLE, advertisement.getTitle());
        pstm.setString(CONTEXT, advertisement.getContext());
        pstm.setString(IMAGE_URL, advertisement.getImageUrl());
        pstm.setString(LANGUAGE, advertisement.getLanguage());
        pstm.setLong(AD_ID, advertisement.getAdId());
    }

    /**
     * Method adds new Advertisement to the Data Base.
     *
     * @param advertisement Advertisement to save
     * @returnto count of added rows
     */
    @Override
    public Integer create(final Advertisement advertisement) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(CREATE_ADVERT);
            pstm.setString(TITLE, advertisement.getTitle());
            pstm.setString(CONTEXT, advertisement.getContext());
            pstm.setString(IMAGE_URL, advertisement.getImageUrl());
            pstm.setString(LANGUAGE, advertisement.getLanguage());
            pstm.setLong(PROV_ID, advertisement.getProvId());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the create method check if the Advertisement table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);

        }
    }

    /**
     * Method returns Advertisemet from Data Base by id.
     *
     * @param adId Advertisement's id
     * @return object Advertisement in Optional
     */
    @Override
    public Optional<Advertisement> getById(final Long adId) throws DaoException {
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(SELECT_ADV_BY_ID);
            pstm.setLong(1, adId);
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getAdvertisementFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method check if the Advertisement table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param adId Advertisement's Id
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Long adId) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(DELETE_ADV_BY_ID);
            pstm.setLong(1, adId);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the delete method check if the Advertisement table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
    }

    /**
     * Update publisher to Data Base.
     *
     * @param advertisement advertisement to update
     * @return count of updated rows
     */
    @Override
    public Integer update(final Advertisement advertisement) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(UPDATE_ADVERT);
            setStatementValues(pstm, advertisement);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String mesage = "Trouble in the update method check if the Advertisement table exists";
            LOGGER.log(Level.WARNING, mesage, ex);
            throw new DaoException(mesage, ex);
        }
    }

    /**
     * Method gets all advertisement from Data Base.
     *
     * @return list of all advertisements or empty otherwise
     */
    @Override
    public List<Advertisement> getAll() throws DaoException {
        final List<Advertisement> advertisementList = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(GET_ALL_ADVERT);
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Advertisement advertisement = getAdvertisementFromResultSet(resultSet);
                advertisementList.add(advertisement);
            }
        } catch (SQLException ex) {
            final String message = "Check if the Advertisement table exists";
            LOGGER.log(Level.WARNING, "", ex);
            throw new DaoException(message, ex);
        }
        return advertisementList;
    }
}
