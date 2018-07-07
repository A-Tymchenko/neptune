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
import com.ra.advertisement.model.entities.Provider;

public final class ProviderAdvertisementDaoImpl implements AdvertisementDao<Provider> {
    private final transient ConnectionFactory connectionFactory;
    private static final String UPDATE_PROVIDER = "update PROVIDER set NAME = ?, ADDRESS= ?, TELEPHONE = ?, "
            + "COUNTRY = ? where PROV_ID = ?";
    private static final String CREATE_PROVIDER = "INSERT INTO PROVIDER (NAME, ADDRESS, TELEPHONE,"
            + "COUNTRY) VALUES(?,?,?,?)";
    private static final String SELECT_PROV_BY_ID = "SELECT * FROM PROVIDER WHERE PROV_ID=?";
    private static final String DELETE_PROV_BY_ID = "DELETE FROM PROVIDER WHERE PROV_ID=?";
    private static final String GET_ALL_PROVIDERS = "SELECT * FROM PROVIDER";
    private static final Integer NAME = 1;
    private static final Integer ADDRESS = 2;
    private static final Integer TELEPHONE = 3;
    private static final Integer COUNTRY = 4;
    private static final Integer PROV_ID = 5;
    private static final Logger LOGGER = Logger.getLogger(ProviderAdvertisementDaoImpl.class.getName());

    public ProviderAdvertisementDaoImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Method extracts a provider from resultSet.
     *
     * @param resultSet resultSet recieved from a Data Base
     * @return provider with the filled fields
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
    private void setStatementValues(final PreparedStatement pstm, final Provider provider) throws SQLException {
        pstm.setString(NAME, provider.getName());
        pstm.setString(ADDRESS, provider.getAddress());
        pstm.setString(TELEPHONE, provider.getTelephone());
        pstm.setString(COUNTRY, provider.getCountry());
        pstm.setLong(PROV_ID, provider.getProvId());
    }

    /**
     * Method adds new Provider to the Data Base.
     *
     * @param provider Provider to save
     * @returnto count of added rows
     */
    @Override
    public Integer create(final Provider provider) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(CREATE_PROVIDER);
            pstm.setString(NAME, provider.getName());
            pstm.setString(ADDRESS, provider.getAddress());
            pstm.setString(TELEPHONE, provider.getTelephone());
            pstm.setString(COUNTRY, provider.getCountry());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "check if the Provider table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
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
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(SELECT_PROV_BY_ID);
            pstm.setLong(1, provId);
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getProviderFomResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method check if the Provider table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param provId Provider's Id
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Long provId) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(DELETE_PROV_BY_ID);
            pstm.setLong(1, provId);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the delete method check if the Provider table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
    }

    /**
     * Update provider to Data Base.
     *
     * @param provider to save.
     * @return count of updated rows.
     */
    @Override
    public Integer update(final Provider provider) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(UPDATE_PROVIDER);
            setStatementValues(pstm, provider);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method check if the Provider table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }

    }

    /**
     * Method gets all providers from Data Base.
     *
     * @return list of providers or empty otherwise
     */
    @Override
    public List<Provider> getAll() throws DaoException {
        final List<Provider> providerList = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(GET_ALL_PROVIDERS);
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Provider provider = getProviderFomResultSet(resultSet);
                providerList.add(provider);
            }
        } catch (SQLException ex) {
            final String message = "Check if the Provider table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
        return providerList;
    }
}
