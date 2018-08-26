package com.ra.advertisement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ra.advertisement.entity.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component("publisherDao")
public class PublisherProjectDaoImpl implements ProjectDao<Publisher> {
    private final transient JdbcTemplate jdbcTemplate;
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();
    private static final String GET_PUB_BY_ID = "SELECT * FROM PUBLISHER WHERE PUB_ID=?";
    private static final Integer NAME = 1;
    private static final Integer ADDRESS = 2;
    private static final Integer TELEPHONE = 3;
    private static final Integer COUNTRY = 4;
    private static final Integer PUB_ID = 5;

    @Autowired
    public PublisherProjectDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds new Publisher to the Data Base.
     *
     * @param publisher Publisher to save
     * @returnto new Publisher
     */
    @Override
    public Publisher create(final Publisher publisher) {
        final String createPublisher = "INSERT INTO PUBLISHER (NAME, ADDRESS, TELEPHONE, COUNTRY) VALUES(?,?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(createPublisher);
                    preparedStatementForCreateOrUpdate(preparedStatement, publisher);
                    return preparedStatement;
                }, keyHolder);
        final Long publisherKey = (Long) keyHolder.getKey();
        publisher.setPubId(publisherKey);
        return publisher;
    }

    /**
     * Method returns Publisher from Data Base by id.
     *
     * @param pubId Publisher's id
     * @return object Publisher
     */
    @Override
    public Publisher getById(final Long pubId) {
        return jdbcTemplate.queryForObject(GET_PUB_BY_ID, BeanPropertyRowMapper.newInstance(Publisher.class), pubId);
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param publisher Publisher we want delete
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Publisher publisher) {
        final String deletePublisher = "DELETE FROM PUBLISHER WHERE PUB_ID=?";
        return jdbcTemplate.update(deletePublisher, publisher.getPubId());
    }

    /**
     * Update publisher to Data Base.
     *
     * @param publisher publisher to update
     * @return new Publisher
     */
    @Override
    public Publisher update(final Publisher publisher) {
        final String updatePublisher = "update PUBLISHER set NAME = ?, ADDRESS= ?, TELEPHONE = ?"
                + ", COUNTRY = ? where PUB_ID = ?";
        jdbcTemplate.update(updatePublisher, ps -> {
            preparedStatementForCreateOrUpdate(ps, publisher);
            ps.setLong(PUB_ID, publisher.getPubId());
        });
        return publisher;
    }

    /**
     * Method gets all publishers from Data Base.
     *
     * @return list of all publishers or empty otherwise
     */
    @Override
    public List<Publisher> getAll() {
        final String getAllPublishers = "SELECT * FROM PUBLISHER";
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(getAllPublishers);
        return mapListFromQueryForList(rows);
    }

    /**
     * We use this method for fill up preparedStatement.
     *
     * @param preparedStatement preparedStatement to fill up
     * @param publisher         publisher where we get fields from
     * @throws SQLException Sqlexception
     */
    private void preparedStatementForCreateOrUpdate(final PreparedStatement preparedStatement,
                                                   final Publisher publisher) throws SQLException {
        preparedStatement.setString(NAME, publisher.getName());
        preparedStatement.setString(ADDRESS, publisher.getAddress());
        preparedStatement.setString(TELEPHONE, publisher.getTelephone());
        preparedStatement.setString(COUNTRY, publisher.getCountry());
    }

    /**
     * this method map listOfCollections from query to list.
     * @param rows rows
     * @return list
     */
    public List<Publisher> mapListFromQueryForList(final List<Map<String, Object>> rows) {
        return rows.stream().map(row -> {
            final Publisher publisher = new Publisher();
            publisher.setPubId((Long) row.get("PUB_ID"));
            publisher.setName((String) row.get("NAME"));
            publisher.setAddress((String) row.get("ADDRESS"));
            publisher.setTelephone((String) row.get("TELEPHONE"));
            publisher.setCountry((String) row.get("COUNTRY"));
            return publisher;
        }).collect(Collectors.toList());
    }
}
