package com.ra.shop.repository.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ra.shop.config.ConnectionFactory;
import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import com.ra.shop.repository.IRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of IRepository interface.
 */
public class UserRepositoryImpl implements IRepository<User> {

    private static final Logger LOGGER = LogManager.getLogger(UserRepositoryImpl.class);
    private static final int PHONE_NUMBER = 1;
    private static final int NAME = 2;
    private static final int SECOND_NAME = 3;
    private static final int COUNTRY = 4;
    private static final int EMAIL_ADDRESS = 5;
    private final transient ConnectionFactory connectionFactory;


    /**
     * Constructs new UserRepositoryImp instance, as an argument accepts ConnectionFactory instance.
     *
     * @param connectionFactory instance.
     */
    public UserRepositoryImpl(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<User> getAll() throws RepositoryException {
        final List<User> all = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS")) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final User user = fillEntityWithValues(resultSet);
                all.add(user);
            }
            return all;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_USER.getMessage(), e);
        }
    }

    @Override
    public User create(final User entity) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                             + "VALUES(?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            setStatementValuesForCreation(statement, entity);
            statement.executeUpdate();
            final ResultSet primaryKeys = statement.getGeneratedKeys();
            if (primaryKeys.next()) {
                entity.setId(primaryKeys.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_USER.getMessage(), e);
        }
        return entity;
    }

//    @Override
//    public Optional<User> get(final long entityId) throws RepositoryException {
//        Objects.requireNonNull(entityId);
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?")) {
//            statement.setLong(1, entityId);
//            final ResultSet res = statement.executeQuery();
//            if (res.next()) {
//                final User found = fillEntityWithValues(res);
//                return Optional.of(found);
//            }
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage(), e);
//            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_USER_BY_ID.getMessage() + " " + entityId, e);
//        }
//        return Optional.empty();
//    }

    @Override
    public User get(final long entityId) throws RepositoryException {
        return null;
    }

    @Override
    public User update(final User newEntity) throws RepositoryException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?,COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?")) {
            setStatementValuesForUpdate(statement, newEntity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_USER.getMessage());
        }
        return newEntity;
    }

    @Override
    public boolean delete(final long entityId) throws RepositoryException {
        Objects.requireNonNull(entityId);
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?");
            statement.setLong(1, entityId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_USER.getMessage(), ex);
        }
    }

    /**
     * Method configuring PrepareStatement for update method.
     *
     * @param statement PreparedStatement.
     * @param newEntity - entity that will be updated.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForUpdate(final PreparedStatement statement, final User newEntity) throws SQLException {
        final int userId = 6;
        statement.setString(PHONE_NUMBER, newEntity.getPhoneNumber());
        statement.setString(NAME, newEntity.getName());
        statement.setString(SECOND_NAME, newEntity.getSecondName());
        statement.setString(COUNTRY, newEntity.getCountry());
        statement.setString(EMAIL_ADDRESS, newEntity.getEmailAddress());
        statement.setLong(userId, newEntity.getId());
    }

    /**
     * Method configuring PrepareStatement for create method.
     *
     * @param preparedStatement PreparedStatement.
     * @param user              new user.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForCreation(final PreparedStatement preparedStatement, final User user) throws SQLException {
        preparedStatement.setString(PHONE_NUMBER, user.getPhoneNumber());
        preparedStatement.setString(NAME, user.getName());
        preparedStatement.setString(SECOND_NAME, user.getSecondName());
        preparedStatement.setString(COUNTRY, user.getCountry());
        preparedStatement.setString(EMAIL_ADDRESS, user.getEmailAddress());
    }

    /**
     * Method returns resultSet with order params inside.
     *
     * @param resultSet resultSet.
     * @return new Order that filled with values from resultSet.
     * @throws SQLException if any error occurs.
     */
    private User fillEntityWithValues(final ResultSet resultSet) throws SQLException {
        final Long userId = resultSet.getLong("USER_ID");
        final String phoneNumber = resultSet.getString("PHONE_NUMBER");
        final String name = resultSet.getString("NAME");
        final String secondName = resultSet.getString("SECOND_NAME");
        final String country = resultSet.getString("COUNTRY");
        final String emailAddress = resultSet.getString("EMAIL_ADDRESS");
        final User user = new User(phoneNumber, name, secondName, country, emailAddress);
        user.setId(userId);
        return user;
    }

}