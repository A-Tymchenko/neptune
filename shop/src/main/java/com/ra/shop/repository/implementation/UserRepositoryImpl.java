package com.ra.shop.repository.implementation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ra.shop.enums.ExceptionMessage;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.User;
import com.ra.shop.repository.IRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * IRepository implementation using Spring jdbc.
 */
@Component
public class UserRepositoryImpl implements IRepository<User> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(UserRepositoryImpl.class);

    /**
     * KeyHolder instance stores primary keys.
     */
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();

    /**
     * JdbcTemplate instance.
     */
    private final transient JdbcTemplate jdbcTemplate;

    /**
     * Constructor accepts jdbcTemplate as a parameter.
     *
     * @param jdbcTemplate jdbcTemplate
     */
    @Autowired
    public UserRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds an entity to database.
     *
     * @param entity that will be created.
     * @return User persisted entity.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public User create(final User entity) throws RepositoryException {
        Objects.requireNonNull(entity);
        final Long userId;
        try {
            jdbcTemplate.update(con -> {
                final PreparedStatement statement = con.prepareStatement(
                        "INSERT INTO USERS (PHONE_NUMBER, NAME, SECOND_NAME, COUNTRY, EMAIL_ADDRESS) "
                                + "VALUES(?, ?, ?, ?, ?)");
                fillEntityWithParameters(statement, entity);
                return statement;
            }, keyHolder);
            userId = (Long) keyHolder.getKey();
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_CREATE_NEW_USER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_CREATE_NEW_USER.getMessage(), e);
        }
        entity.setId(userId);
        return entity;
    }

    /**
     * Method performs search in database and returns an entity with pointed id.
     *
     * @param entityId - id of searched entity.
     * @return User searched entity.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public User get(final long entityId) throws RepositoryException {
        final User found;
        try {
            final BeanPropertyRowMapper<User> mapper = BeanPropertyRowMapper.newInstance(User.class);
            found = jdbcTemplate.queryForObject(
                    "SELECT * FROM USERS WHERE USER_ID = ?",
                    mapper,
                    entityId);
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_USER_BY_ID.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_USER_BY_ID.getMessage(), e);
        }
        return found;
    }

    /**
     * Method performs update operation.
     *
     * @param newEntity updated version of entity.
     * @return User updated version of entity.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public User update(final User newEntity) throws RepositoryException {
        Objects.requireNonNull(newEntity);
        try {
            jdbcTemplate.update(
                    "UPDATE USERS SET PHONE_NUMBER= ?,NAME = ?,SECOND_NAME= ?,COUNTRY= ?,EMAIL_ADDRESS= ? WHERE USER_ID= ?",
                    statement -> {
                        fillEntityWithParameters(statement, newEntity);
                        final int userId = 6;
                        statement.setLong(userId, newEntity.getId());
                    });
            return newEntity;
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_UPDATE_USER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_UPDATE_USER.getMessage(), e);
        }
    }

    /**
     * Method performs delete operation.
     *
     * @param entityId of entity that will be deleted.
     * @return boolean true if entity removed successfully or false if not.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public boolean delete(final long entityId) throws RepositoryException {
        try {
            final int deletedRowsNumber = jdbcTemplate.update(
                    "DELETE FROM USERS WHERE USER_ID = ?",
                    entityId);
            return deletedRowsNumber > 0;
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_DELETE_USER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_DELETE_USER.getMessage(), e);
        }
    }

    /**
     * Method returns list of entities that stored in database.
     *
     * @return List list of existed users.
     * @throws RepositoryException can be thrown if any error occurs.
     */
    @Override
    public List<User> getAll() throws RepositoryException {
        final List<Map<String, Object>> map;
        try {
            map = jdbcTemplate.queryForList("SELECT * FROM USERS");
            getListOfUsers(map);
        } catch (DataAccessException e) {
            LOGGER.error(ExceptionMessage.FAILED_TO_GET_ALL_USER.getMessage(), e);
            throw new RepositoryException(ExceptionMessage.FAILED_TO_GET_ALL_USER.getMessage(), e);
        }
        return getListOfUsers(map);
    }

    /**
     * Method iterate over the list of users, set params into user instances and adds them to ArrayList and returns.
     *
     * @param map stores params for each entity.
     * @return List list of users.
     */
    public List<User> getListOfUsers(final List<Map<String, Object>> map) {
        final List<User> users = new ArrayList<>();
        final User user = new User();
        for (final Map<String, Object> row : map) {
            user.setId((Long) row.get("USER_ID"));
            user.setPhoneNumber((String) row.get("PHONE_NUMBER"));
            user.setName((String) row.get("NAME"));
            user.setSecondName((String) row.get("SECOND_NAME"));
            user.setCountry((String) row.get("COUNTRY"));
            user.setEmailAddress((String) row.get("EMAIL_ADDRESS"));
            users.add(user);
        }
        return users;
    }

    /**
     * Method fills user entity with parameters using preparedStatement.
     *
     * @param statement PreparedStatement.
     * @param user      user entity that will be created.
     * @throws SQLException if any error occurs.
     */
    private void fillEntityWithParameters(final PreparedStatement statement, final User user) throws SQLException {
        final int phoneNumber = 1;
        final int name = 2;
        final int secondName = 3;
        final int country = 4;
        final int emailAddress = 5;
        statement.setString(phoneNumber, user.getPhoneNumber());
        statement.setString(name, user.getName());
        statement.setString(secondName, user.getSecondName());
        statement.setString(country, user.getCountry());
        statement.setString(emailAddress, user.getEmailAddress());
    }
}
