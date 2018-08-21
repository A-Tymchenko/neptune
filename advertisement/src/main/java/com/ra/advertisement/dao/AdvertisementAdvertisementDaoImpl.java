package com.ra.advertisement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ra.advertisement.entity.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component("advertDao")
public class AdvertisementAdvertisementDaoImpl implements AdvertisementDao<Advertisement> {
    private final transient JdbcTemplate jdbcTemplate;
    private final transient KeyHolder keyHolder = new GeneratedKeyHolder();
    private static final String GET_ADVERT_BY_ID = "SELECT * FROM ADVERTISEMENT WHERE AD_ID=?";
    private static final Integer TITLE = 1;
    private static final Integer CONTEXT = 2;
    private static final Integer IMAGE_URL = 3;
    private static final Integer LANGUAGE = 4;
    private static final Integer AD_ID = 5;

    @Autowired
    public AdvertisementAdvertisementDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds new Advertisement to the Data Base.
     *
     * @param advertisement Advertisement to save
     * @returnto new Advertisement
     */
    @Override
    public Advertisement create(final Advertisement advertisement) {
        final String createAdvert = "INSERT INTO ADVERTISEMENT (TITLE, CONTEXT, IMAGE_URL, LANGUAGE) "
                + "VALUES(?,?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement(createAdvert);
                    preparedStatementForCreateOrUpdate(preparedStatement, advertisement);
                    return preparedStatement;
                }, keyHolder);
        final Long advertKey = (Long) keyHolder.getKey();
        advertisement.setAdId(advertKey);
        return advertisement;
    }

    /**
     * Method returns Advertisemet from Data Base by id.
     *
     * @param adId Advertisement's id
     * @return object Advertisement
     */
    @Override
    public Advertisement getById(final Long adId) {
        return jdbcTemplate.queryForObject(GET_ADVERT_BY_ID, BeanPropertyRowMapper.newInstance(Advertisement.class), adId);
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param advertisement Advertisement we want delete
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Advertisement advertisement) {
        final String deleteAdvert = "DELETE FROM ADVERTISEMENT WHERE AD_ID=?";
        return jdbcTemplate.update(deleteAdvert, advertisement.getAdId());
    }

    /**
     * Update advert to Data Base.
     *
     * @param advertisement advertisement to update
     * @return new Advertisement
     */
    @Override
    public Advertisement update(final Advertisement advertisement) {
        final String updateAdvert = "update ADVERTISEMENT set TITLE = ?, CONTEXT= ?, IMAGE_URL = ?,"
                + " LANGUAGE = ? where AD_ID = ?";
        jdbcTemplate.update(updateAdvert, ps -> {
            preparedStatementForCreateOrUpdate(ps, advertisement);
            ps.setLong(AD_ID, advertisement.getAdId());
        });
        return advertisement;
    }

    /**
     * Method gets all advertisement from Data Base.
     *
     * @return list of all advertisements or empty otherwise
     */
    @Override
    public List<Advertisement> getAll() {
        final String getAllAdverts = "SELECT * FROM ADVERTISEMENT";
        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(getAllAdverts);
        return mapListFromQueryForList(rows);
    }

    /**
     * We use this method for fill up preparedStatement.
     *
     * @param preparedStatement preparedStatement to fill up
     * @param advertisement     advertisement where we get fields from
     * @throws SQLException Sqlexception
     */
    private void preparedStatementForCreateOrUpdate(final PreparedStatement preparedStatement,
                                                   final Advertisement advertisement) throws SQLException {
        preparedStatement.setString(TITLE, advertisement.getTitle());
        preparedStatement.setString(CONTEXT, advertisement.getContext());
        preparedStatement.setString(IMAGE_URL, advertisement.getImageUrl());
        preparedStatement.setString(LANGUAGE, advertisement.getLanguage());
    }

    /**
     * this method map listOfCollections from query to list.
     * @param rows rows
     * @return list
     */
    public List<Advertisement> mapListFromQueryForList(final List<Map<String, Object>> rows) {
        return rows.stream().map(row -> {
            final Advertisement advertisement = new Advertisement();
            advertisement.setAdId((Long) row.get("AD_ID"));
            advertisement.setTitle((String) row.get("TITLE"));
            advertisement.setContext((String) row.get("CONTEXT"));
            advertisement.setImageUrl((String) row.get("IMAGE_URL"));
            advertisement.setLanguage((String) row.get("LANGUAGE"));
            return advertisement;
        }).collect(Collectors.toList());
    }
}
