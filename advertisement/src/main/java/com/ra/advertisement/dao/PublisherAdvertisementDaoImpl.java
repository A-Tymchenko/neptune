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
import com.ra.advertisement.entity.Publisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PublisherAdvertisementDaoImpl implements AdvertisementDao<Publisher> {
    private final transient ConnectionFactory connectionFactory;
    private static final Integer NAME = 1;
    private static final Integer ADDRESS = 2;
    private static final Integer TELEPHONE = 3;
    private static final Integer COUNTRY = 4;
    private static final Integer PUB_ID = 5;
    private static final Logger LOGGER = LogManager.getLogger(PublisherAdvertisementDaoImpl.class);

    public PublisherAdvertisementDaoImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Method adds new Publisher to the Data Base.
     *
     * @param publisher Publisher to save
     * @return entity Publisher with generated id;
     */
    @Override
    public Publisher create(final Publisher publisher) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("INSERT INTO PUBLISHER (NAME, ADDRESS, "
                    + "TELEPHONE, COUNTRY) VALUES(?,?,?,?)");
            setStatementValues(pstm, publisher);
            pstm.executeUpdate();
            final ResultSet resultSetWithKey = pstm.getGeneratedKeys();
            if (resultSetWithKey.next()) {
                return getById(resultSetWithKey.getLong(1)).get();
            }
            return publisher;
        } catch (SQLException ex) {
            final String message = "Trouble in the create method {}";
            LOGGER.error(message, AdvertisementEnum.PUBLISHER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PUBLISHER.getMessage()), ex);
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
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM PUBLISHER WHERE PUB_ID=?");
            pstm.setLong(1, pubId);
            final ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getPublisherFromResultSet(resultSet));
            }
        } catch (SQLException ex) {
            final String message = "Trouble in the getById method {}";
            LOGGER.error(message, AdvertisementEnum.PUBLISHER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PUBLISHER.getMessage()), ex);
        }
        return Optional.empty();
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param publisher Publisher
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Publisher publisher) throws DaoException {
        if (publisher != null) {
            try (Connection connection = connectionFactory.getConnection()) {
                final PreparedStatement pstm = connection.prepareStatement("DELETE FROM PUBLISHER WHERE PUB_ID=?");
                pstm.setLong(1, publisher.getPubId());
                return pstm.executeUpdate();
            } catch (SQLException ex) {
                final String message = "Trouble in the delete method {}";
                LOGGER.error(message, AdvertisementEnum.PUBLISHER.getMessage(), ex);
                throw new DaoException(String.format(message, AdvertisementEnum.PUBLISHER.getMessage()), ex);
            }
        } else {
            return 0;
        }
    }

    /**
     * Update publisher to Data Base.
     *
     * @param publisher to save.
     * @return new Publisher updated.
     */
    @Override
    public Publisher update(final Publisher publisher) throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement pstm = connection.prepareStatement("update PUBLISHER set NAME = ?, ADDRESS= ?, "
                    + "TELEPHONE = ?, COUNTRY = ? where PUB_ID = ?");
            setStatementValues(pstm, publisher);
            pstm.setLong(PUB_ID, publisher.getPubId());
            pstm.executeUpdate();
            return getById(publisher.getPubId()).get();
        } catch (SQLException ex) {
            final String message = "Trouble in the update method {}";
            LOGGER.error(message, AdvertisementEnum.PUBLISHER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PUBLISHER.getMessage()), ex);
        }
    }

    /**
     * Method gets all publishers from Data Base.
     *
     * @return list of publishers or empty otherwise
     */
    @Override
    public List<Publisher> getAll() throws DaoException {
        try (Connection connection = connectionFactory.getConnection()) {
            final List<Publisher> publishersList = new ArrayList<>();
            final PreparedStatement pstm = connection.prepareStatement("SELECT * FROM PUBLISHER");
            final ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                final Publisher publisher = getPublisherFromResultSet(resultSet);
                publishersList.add(publisher);
            }
            return publishersList;
        } catch (SQLException ex) {
            final String message = "Trouble in the getAll method {}";
            LOGGER.error(message, AdvertisementEnum.PUBLISHER.getMessage(), ex);
            throw new DaoException(String.format(message, AdvertisementEnum.PUBLISHER.getMessage()), ex);
        }
    }

    /**
     * Method extracts a publisher from resultSet.
     *
     * @param resultSet resultSet received from a Data Base
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
    }
}
