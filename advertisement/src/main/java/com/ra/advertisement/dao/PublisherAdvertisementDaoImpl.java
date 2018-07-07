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
import com.ra.advertisement.model.entities.Publisher;

public final class PublisherAdvertisementDaoImpl implements AdvertisementDao<Publisher> {
    private final transient ConnectionFactory connectionFactory;
    private static final String UPDATE_PUBLISHER = "update PUBLISHER set NAME = ?, ADDRESS= ?, TELEPHONE = ?, "
            + "COUNTRY = ? where PUB_ID = ?";
    private static final String CREATE_PUBLISHER = "INSERT INTO PUBLISHER (NAME, ADDRESS, TELEPHONE,"
            + "COUNTRY) VALUES(?,?,?,?)";
    private static final String SELECT_PUBL_BY_ID = "SELECT * FROM PUBLISHER WHERE PUB_ID=?";
    private static final String DELETE_PUBL_BY_ID = "DELETE FROM PUBLISHER WHERE PUB_ID=?";
    private static final String GET_ALL_PUBL = "SELECT * FROM PUBLISHER";
    private static final Integer NAME = 1;
    private static final Integer ADDRESS = 2;
    private static final Integer TELEPHONE = 3;
    private static final Integer COUNTRY = 4;
    private static final Integer PUB_ID = 5;
    private static final Logger LOGGER = Logger.getLogger(PublisherAdvertisementDaoImpl.class.getName());

    public PublisherAdvertisementDaoImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Method extracts a publisher from resultSet.
     *
     * @param resultSet resultSet recieved from a Data Base
     * @return publisher with the filled fields
     */
    private Publisher getPublisherFromResultSet(final ResultSet resultSet) throws SQLException {
        final Publisher publisher = new Publisher();
        publisher.setPubId(resultSet.getLong("PUB_ID"));
        publisher.setName(resultSet.getString("NAME"));
        publisher.setAddress(resultSet.getString("ADDRESS"));
        publisher.setTelephone(resultSet.getString("TELEPHONE"));
        publisher.setCountry(resultSet.getString("COUNTRY"));
        return publisher;

    }

    /**
     * Method sets Statement values.
     *
     * @param pstm      to save
     * @param publisher to save
     */
    private void setStatementValues(final PreparedStatement pstm, final Publisher publisher) throws SQLException {
        pstm.setString(NAME, publisher.getName());
        pstm.setString(ADDRESS, publisher.getAddress());
        pstm.setString(TELEPHONE, publisher.getTelephone());
        pstm.setString(COUNTRY, publisher.getCountry());
        pstm.setLong(PUB_ID, publisher.getPubId());
    }

    /**
     * Method adds new Publisher to the Data Base.
     *
     * @param publisher Publisher to save
     * @returnto count of added rows
     */
    @Override
    public Integer create(final Publisher publisher) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(CREATE_PUBLISHER);
            pstm.setString(NAME, publisher.getName());
            pstm.setString(ADDRESS, publisher.getAddress());
            pstm.setString(TELEPHONE, publisher.getTelephone());
            pstm.setString(COUNTRY, publisher.getCountry());
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the create method check if the Publisher table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }

    }

    /**
     * Method returns Publisher from Data Base by id.
     *
     * @param pubId Publisher's id
     * @return object Publisher in Optional
     */
    @Override
    public Optional<Publisher> getById(final Long pubId) throws DaoException {
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(SELECT_PUBL_BY_ID);
            pstm.setLong(1, pubId);
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getPublisherFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method check if the Publisher table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes  the object from Data Base by its id.
     *
     * @param pubId Publisher's Id
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Long pubId) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(DELETE_PUBL_BY_ID);
            pstm.setLong(1, pubId);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the delete method check if the Publisher table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
    }

    /**
     * Update publisher to Data Base.
     *
     * @param publisher to save.
     * @return count of updated rows.
     */
    @Override
    public Integer update(final Publisher publisher) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(UPDATE_PUBLISHER);
            setStatementValues(pstm, publisher);
            return pstm.executeUpdate();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method check if the Publisher table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
    }

    /**
     * Method gets all publishers from Data Base.
     *
     * @return list of publishers or empty otherwise
     */
    @Override
    public List<Publisher> getAll() throws DaoException {
        final List<Publisher> publishersList = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement(GET_ALL_PUBL);
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Publisher publisher = getPublisherFromResultSet(resultSet);
                publishersList.add(publisher);
            }
        } catch (SQLException ex) {
            final String message = "Check if the Publisher table exists";
            LOGGER.log(Level.WARNING, message, ex);
            throw new DaoException(message, ex);
        }
        return publishersList;
    }
}
