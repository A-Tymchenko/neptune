package com.ra.advertisement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ra.advertisement.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component("providerDao")
public final class ProviderAdvertisementDaoImpl implements AdvertisementDao<Provider> {
    private final transient JdbcTemplate jdbcTemplate;
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();
    private static final String GET_PROV_BY_ID = "SELECT * FROM PROVIDER WHERE PROV_ID=?";
    private static final Integer NAME = 1;
    private static final Integer ADDRESS = 2;
    private static final Integer TELEPHONE = 3;
    private static final Integer COUNTRY = 4;
    private static final Integer PROV_ID = 5;

    @Autowired
    public ProviderAdvertisementDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds new Provider to the Data Base.
     *
     * @param provider Provider to save
     * @returnto new Provider
     */
    @Override
    public Provider create(final Provider provider) {
        final String createProvider = "INSERT INTO PROVIDER (NAME, ADDRESS, TELEPHONE, COUNTRY) VALUES(?,?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(createProvider);
                    preparedStatementForCreateOrUpdate(preparedStatement, provider);
                    return preparedStatement;
                }, keyHolder);
        final Long providerKey = (Long) keyHolder.getKey();
        provider.setProvId(providerKey);
        return provider;
    }

    /**
     * Method returns Provider from Data Base by id.
     *
     * @param provId Provider's id
     * @return object Provider
     */
    @Override
    public Provider getById(final Long provId) {
        return jdbcTemplate.queryForObject(GET_PROV_BY_ID, BeanPropertyRowMapper.newInstance(Provider.class), provId);
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param provider Provider we want delete
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Provider provider) {
        final String deleteAdvert = "DELETE FROM PROVIDER WHERE PROV_ID=?";
        return jdbcTemplate.update(deleteAdvert, provider.getProvId());
    }

    /**
     * Update provider to Data Base.
     *
     * @param provider provider to update
     * @return new Provider
     */
    @Override
    public Provider update(final Provider provider) {
        final String updateProvider = "update PROVIDER set NAME = ?, ADDRESS= ?, TELEPHONE = ?,"
                + " COUNTRY = ? where PROV_ID = ?";
        jdbcTemplate.update(updateProvider, ps -> {
            preparedStatementForCreateOrUpdate(ps, provider);
            ps.setLong(PROV_ID, provider.getProvId());
        });
        return provider;
    }

    /**
     * Method gets all providers from Data Base.
     *
     * @return list of all providers or empty otherwise
     */
    @Override
    public List<Provider> getAll() {
        final String getAllProviders = "SELECT * FROM PROVIDER";
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(getAllProviders);
        return mapListFromQueryForList(rows);
    }

    /**
     * We use this method for fill up preparedStatement.
     *
     * @param preparedStatement preparedStatement to fill up
     * @param provider          provider where we get fields from
     * @throws SQLException Sqlexception
     */
    private void preparedStatementForCreateOrUpdate(final PreparedStatement preparedStatement,
                                                   final Provider provider) throws SQLException {
        preparedStatement.setString(NAME, provider.getName());
        preparedStatement.setString(ADDRESS, provider.getAddress());
        preparedStatement.setString(TELEPHONE, provider.getTelephone());
        preparedStatement.setString(COUNTRY, provider.getCountry());
    }

    /**
     * this method map listOfCollections from query to list.
     * @param rows rows
     * @return list
     */
    public List<Provider> mapListFromQueryForList(final List<Map<String, Object>> rows) {
        return rows.stream().map(row -> {
            final Provider provider = new Provider();
            provider.setProvId((Long) row.get("PROV_ID"));
            provider.setName((String) row.get("NAME"));
            provider.setAddress((String) row.get("ADDRESS"));
            provider.setTelephone((String) row.get("TELEPHONE"));
            provider.setCountry((String) row.get("COUNTRY"));
            return provider;
        }).collect(Collectors.toList());
    }
}
