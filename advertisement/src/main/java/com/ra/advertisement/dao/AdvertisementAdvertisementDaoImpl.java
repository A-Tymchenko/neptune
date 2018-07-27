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
import com.ra.advertisement.entity.Advertisement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AdvertisementAdvertisementDaoImpl implements AdvertisementDao<Advertisement> {
    private final transient ConnectionFactory connectionFactory;
    private static final Integer TITLE = 1;
    private static final Integer CONTEXT = 2;
    private static final Integer IMAGE_URL = 3;
    private static final Integer LANGUAGE = 4;
    private static final Integer AD_ID = 5;
    private static final Logger LOGGER = LogManager.getLogger(AdvertisementAdvertisementDaoImpl.class);

    public AdvertisementAdvertisementDaoImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Method adds new Advertisement to the Data Base.
     *
     * @param advertisement Advertisement to save
     * @returnto new Advertisement
     */
    @Override
    public Advertisement create(final Advertisement advertisement) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("INSERT INTO ADVERTISEMENT "
                    + "(TITLE, CONTEXT, IMAGE_URL, LANGUAGE) VALUES(?,?,?,?)");
            setStatementValues(pstm, advertisement);
            pstm.executeUpdate();
            final ResultSet resultSetWithKey = pstm.getGeneratedKeys();
            if (resultSetWithKey.next()) {
                return getById(resultSetWithKey.getLong(1)).get();
            }
            return advertisement;
        } catch (SQLException ex) {
            final String message = "Trouble in the create method {}";
            LOGGER.error(message, AdvertisementEnum.ADVERTISEMENT.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.ADVERTISEMENT.getMessage()), ex);
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
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM ADVERTISEMENT WHERE AD_ID=?");
            pstm.setLong(1, adId);
            final ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getAdvertisementFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method {}";
            LOGGER.error(message, AdvertisementEnum.ADVERTISEMENT.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.ADVERTISEMENT.getMessage()), ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param advertisement Advertisement we want delete
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Advertisement advertisement) throws DaoException {
        if (advertisement != null) {
            try (Connection connection = connectionFactory.getConnection()) {
                final PreparedStatement pstm = connection.prepareStatement("DELETE FROM ADVERTISEMENT WHERE AD_ID=?");
                pstm.setLong(1, advertisement.getAdId());
                return pstm.executeUpdate();
            } catch (SQLException ex) {
                final String message = "Trouble in the delete method {}";
                LOGGER.error(message, AdvertisementEnum.ADVERTISEMENT.getMessage(), ex);
                throw new DaoException(String.format(message, AdvertisementEnum.ADVERTISEMENT.getMessage()), ex);
            }
        } else {
            return 0;
        }
    }

    /**
     * Update publisher to Data Base.
     *
     * @param advertisement advertisement to update
     * @return new Advertisement
     */
    @Override
    public Advertisement update(final Advertisement advertisement) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("update ADVERTISEMENT set TITLE = ?, "
                    + "CONTEXT= ?, IMAGE_URL = ?, LANGUAGE = ? where AD_ID = ?");
            setStatementValues(pstm, advertisement);
            pstm.setLong(AD_ID, advertisement.getAdId());
            pstm.executeUpdate();
            return getById(advertisement.getAdId()).get();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method {}";
            LOGGER.error(message, AdvertisementEnum.ADVERTISEMENT.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.ADVERTISEMENT.getMessage()), ex);
        }
    }

    /**
     * Method gets all advertisement from Data Base.
     *
     * @return list of all advertisements or empty otherwise
     */
    @Override
    public List<Advertisement> getAll() throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final List<Advertisement> advertisementList = new ArrayList<>();
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM ADVERTISEMENT");
            final ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Advertisement advertisement = getAdvertisementFromResultSet(resultSet);
                advertisementList.add(advertisement);
            }
            return advertisementList;
        } catch (SQLException ex) {
            final String message = "Trouble in the getAll method {}";
            LOGGER.error(message, AdvertisementEnum.ADVERTISEMENT.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.ADVERTISEMENT.getMessage()), ex);
        }
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
        return advertisement;
    }

    /**
     * Method sets Statement for create method.
     *
     * @param pstm          to save
     * @param advertisement to save
     */
    private void setStatementValues(final PreparedStatement pstm, final Advertisement advertisement) throws SQLException {
        pstm.setString(TITLE, advertisement.getTitle());
        pstm.setString(CONTEXT, advertisement.getContext());
        pstm.setString(IMAGE_URL, advertisement.getImageUrl());
        pstm.setString(LANGUAGE, advertisement.getLanguage());
    }
}
