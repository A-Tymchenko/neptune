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
import com.ra.advertisement.entity.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ProviderAdvertisementDaoImpl implements AdvertisementDao<Provider> {
    private final transient ConnectionFactory connectionFactory;
    private static final Integer NAME = 1;
    private static final Integer ADDRESS = 2;
    private static final Integer TELEPHONE = 3;
    private static final Integer COUNTRY = 4;
    private static final Integer PROV_ID = 5;
    private static final Logger LOGGER = LogManager.getLogger(ProviderAdvertisementDaoImpl.class);

    public ProviderAdvertisementDaoImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Method adds new Provider to the Data Base.
     *
     * @param provider Provider to save
     * @return entity Provider with generated key
     */
    @Override
    public Provider create(final Provider provider) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("INSERT INTO PROVIDER (NAME, ADDRESS, "
                    + "TELEPHONE, COUNTRY) VALUES(?,?,?,?)");
            setStatementValues(pstm, provider);
            pstm.executeUpdate();
            final ResultSet resultSetWithKey = pstm.getGeneratedKeys();
            if (resultSetWithKey.next()) {
                return getById(resultSetWithKey.getLong(1)).get();
            }
            return provider;
        } catch (SQLException ex) {
            final String message = "Trouble in the create method {}";
            LOGGER.error(message, AdvertisementEnum.PROVIDER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PROVIDER.getMessage()), ex);
        }
    }

    /**
     * Method returns Provider from Data Base by id.
     *
     * @param provId Provider's id
     * @return object Provider in Optional
     */
    @Override
    public Optional<Provider> getById(final Long provId) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM PROVIDER WHERE PROV_ID=?");
            pstm.setLong(1, provId);
            final ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getProviderFomResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method {}";
            LOGGER.error(message, AdvertisementEnum.PROVIDER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PROVIDER.getMessage()), ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param provider Provider
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Provider provider) throws DaoException {
        if (provider != null) {
            try (Connection connection = connectionFactory.getConnection()) {
                final PreparedStatement pstm = connection.prepareStatement("DELETE FROM PROVIDER WHERE PROV_ID=?");
                pstm.setLong(1, provider.getProvId());
                return pstm.executeUpdate();
            } catch (SQLException ex) {
                final String message = "Trouble in the delete method {}";
                LOGGER.error(message, AdvertisementEnum.PROVIDER.getMessage(), ex);
                throw new DaoException(String.format(message, AdvertisementEnum.PROVIDER.getMessage()), ex);
            }
        } else {
            return 0;
        }
    }

    /**
     * Update provider to Data Base.
     *
     * @param provider to save
     * @return new entity Provider updated
     */
    @Override
    public Provider update(final Provider provider) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("update PROVIDER set NAME = ?, ADDRESS= ?, "
                    + "TELEPHONE = ?, COUNTRY = ? where PROV_ID = ?");
            setStatementValues(pstm, provider);
            pstm.setLong(PROV_ID, provider.getProvId());
            pstm.executeUpdate();
            return getById(provider.getProvId()).get();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method {}";
            LOGGER.error(message, AdvertisementEnum.PROVIDER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PROVIDER.getMessage()), ex);
        }
    }

    /**
     * Method gets all providers from Data Base.
     *
     * @return list of providers or empty otherwise
     */
    @Override
    public List<Provider> getAll() throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final List<Provider> providerList = new ArrayList<>();
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM PROVIDER");
            final ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Provider provider = getProviderFomResultSet(resultSet);
                providerList.add(provider);
            }
            return providerList;
        } catch (SQLException ex) {
            final String message = "Trouble in the getAll method {}";
            LOGGER.error(message, AdvertisementEnum.PROVIDER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PROVIDER.getMessage()), ex);
        }
    }

    /**
     * Method extracts a provider from resultSet.
     *
     * @param resultSet resultSet received from a Data Base
     * @return new Provider with the filled fields
     */
    private Provider getProviderFomResultSet(final ResultSet resultSet) throws SQLException {
        final Provider provider = new Provider();
        provider.setProvId(resultSet.getLong("PROV_ID"));
        provider.setName(resultSet.getString("NAME"));
        provider.setAddress(resultSet.getString("ADDRESS"));
        provider.setTelephone(resultSet.getString("TELEPHONE"));
        provider.setCountry(resultSet.getString("COUNTRY"));
        return provider;
    }

    /**
     * Method sets Statement values.
     *
     * @param pstm     to save
     * @param provider to save
     */
    private void setStatementValues(final PreparedStatement pstm, final Provider provider) throws
            SQLException {
        pstm.setString(NAME, provider.getName());
        pstm.setString(ADDRESS, provider.getAddress());
        pstm.setString(TELEPHONE, provider.getTelephone());
        pstm.setString(COUNTRY, provider.getCountry());
    }
}
