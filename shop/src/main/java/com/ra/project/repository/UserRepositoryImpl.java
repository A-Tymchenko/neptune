package com.ra.project.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ra.project.configuration.ConnectionFactory;
import com.ra.project.exceptions.UserException;
import com.ra.project.model.User;
import org.apache.log4j.Logger;

/**
 * Implementation of IRepository interface.
 */
public class UserRepositoryImpl implements IRepository<User> {

    private static final Logger LOGGER = Logger.getLogger(UserException.class);
    private static final int USER_ID = 1;
    private static final int PHONE_NUMBER = 2;
    private static final int NAME = 3;
    private static final int SECOND_NAME = 4;
    private static final int COUNTRY = 5;
    private static final int EMAIL_ADDRESS = 6;

    /**
     * Field connectionFactory.
     */
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
    public List<User> getAll() throws UserException {
        final List<User> all = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS")) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final User user = fillEntityWithValues(resultSet);
                all.add(user);
            }
            if (all.size() > 0) {
                return all;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new UserException("Can`t get user`s list!");
        }
        return Collections.emptyList();
    }

    @Override
    public User create(final User entity) throws UserException {
        Objects.requireNonNull(entity);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO USERS VALUES(?, ?, ?, ?, ?, ?)")) {
            setStatementValuesForCreation(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new UserException("User creation is failed!");
        }
        return entity;
    }

    @Override
    public Optional<User> get(final Long entityId) throws UserException {
        Objects.requireNonNull(entityId);
        User found;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?")) {
            statement.setLong(1, entityId);
            final ResultSet res = statement.executeQuery();
            if (res.next()) {
                found = fillEntityWithValues(res);
                return Optional.of(found);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new UserException(String.format("Can`t get an user with id : %d", entityId));
        }
        return Optional.empty();
    }

    @Override
    public User update(final User newEntity) throws UserException {
        Objects.requireNonNull(newEntity);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE USERS SET PHONE_NUMBER = ?, NAME = ?, SECOND_NAME = ?, " +
                             "COUNTRY = ?, EMAIL_ADDRESS = ? WHERE USER_ID = ?")) {
            setStatementValuesForUpdate(statement, newEntity);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new UserException("User updating failed!");
        }
        return newEntity;
    }

//    @Override
//    public Boolean delete(final Long entityId) throws  UserException {
//        Objects.requireNonNull(entityId);
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?")) {
//            statement.setLong(1, entityId);
//            final int deleted = statement.executeUpdate();
//            if (deleted > 0) {
//                return Boolean.TRUE;
//            }
//        } catch (SQLException e) {
//            LOGGER.error(e.getMessage());
//            throw new UserException("User deletion failed!");
//        }
//        return Boolean.FALSE;
//    }



    @Override
    public Boolean delete(final Long entityId) throws UserException {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM USERS WHERE USER_ID = ?");
            statement.setLong(1, entityId);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
            throw new UserException(ex.getMessage(), ex);
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
        final int phoneNumber = 1;
        final int name = 2;
        final int secondName = 3;
        final int country = 4;
        final int emailAddress = 5;
        final int userId = 6;
        statement.setString(phoneNumber, newEntity.getPhoneNumber());
        statement.setString(name, newEntity.getName());
        statement.setString(secondName, newEntity.getSecondName());
        statement.setString(country, newEntity.getCountry());
        statement.setString(emailAddress, newEntity.getEmailAddress());
        statement.setLong(userId, newEntity.getId());
    }

    /**
     * Method configuring PrepareStatement for create method.
     * @param preparedStatement PreparedStatement.
     * @param user new user.
     * @throws SQLException if any error occurs.
     */
    private void setStatementValuesForCreation(final PreparedStatement preparedStatement, final User user) throws SQLException {
        preparedStatement.setLong(USER_ID, user.getId());
        preparedStatement.setString(PHONE_NUMBER, user.getPhoneNumber());
        preparedStatement.setString(NAME, user.getName());
        preparedStatement.setString(SECOND_NAME, user.getSecondName());
        preparedStatement.setString(COUNTRY, user.getCountry());
        preparedStatement.setString(EMAIL_ADDRESS, user.getEmailAddress());
    }

    /**
     * Method returns resultSet with user params inside.
     *
     * @param resultSet resultSet.
     * @return new USer that filled with values from resultSet.
     * @throws SQLException if any error occurs.
     */
    private User fillEntityWithValues(final ResultSet resultSet) throws SQLException {
        final Long userId = resultSet.getLong("USER_ID");
        final String phoneNumber = resultSet.getString("PHONE_NUMBER");
        final String name = resultSet.getString("NAME");
        final String secondName = resultSet.getString("SECOND_NAME");
        final String country = resultSet.getString("COUNTRY");
        final String emailAddress = resultSet.getString("EMAIL_ADDRESS");
        return new User(userId, phoneNumber, name, secondName, country, emailAddress);
    }

}
