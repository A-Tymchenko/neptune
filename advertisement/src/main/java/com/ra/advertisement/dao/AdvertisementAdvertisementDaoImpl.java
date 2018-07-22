package com.ra.advertisement.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ra.advertisement.dao.exceptions.DaoException;
import com.ra.advertisement.entity.Advertisement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public final class AdvertisementAdvertisementDaoImpl implements AdvertisementDao<Advertisement> {
    private static final String CREATE_ADVERT = "INSERT INTO ADVERTISEMENT (TITLE, CONTEXT, IMAGE_URL, LANGUAGE) "
            + "VALUES(?,?,?,?)";
    private static final String GET_ADVERT_BY_ID = "SELECT * FROM ADVERTISEMENT WHERE AD_ID=?";
    private static final String GET_ALL_ADVERTS = "SELECT * FROM ADVERTISEMENT";
    private static final String UPDATE_ADVERT = "update ADVERTISEMENT set TITLE = ?, CONTEXT= ?, IMAGE_URL = ?,"
            + " LANGUAGE = ? where AD_ID = ?";
    private static final String DELETE_ADVERT = "DELETE FROM ADVERTISEMENT WHERE AD_ID=?";
    private static final Logger LOGGER = LogManager.getLogger(AdvertisementAdvertisementDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public AdvertisementAdvertisementDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Method adds new Advertisement to the Data Base.
     *
     * @param advertisement Advertisement to save
     * @returnto new Advertisement
     */
    @Override
    public Advertisement create(final Advertisement advertisement) throws DaoException {
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(CREATE_ADVERT);
                    ps.setString(1, advertisement.getTitle());
                    ps.setString(2, advertisement.getContext());
                    ps.setString(3, advertisement.getImageUrl());
                    ps.setString(4, advertisement.getLanguage());
                    return ps;
                }, keyHolder);
        final Long advertKey = (Long) keyHolder.getKey();
        return jdbcTemplate.queryForObject(GET_ADVERT_BY_ID, BeanPropertyRowMapper.newInstance(Advertisement.class), advertKey);
    }

    /**
     * Method returns Advertisemet from Data Base by id.
     *
     * @param adId Advertisement's id
     * @return object Advertisement
     */
    @Override
    public Advertisement getById(final Long adId) throws DaoException {
        return jdbcTemplate.queryForObject(GET_ADVERT_BY_ID, BeanPropertyRowMapper.newInstance(Advertisement.class), adId);
    }

    /**
     * Method deletes the object from Data Base by its id.
     *
     * @param advertisement Advertisement we want delete
     * @return count of deleted rows
     */
    @Override
    public Integer delete(final Advertisement advertisement) throws DaoException {
        return jdbcTemplate.update(DELETE_ADVERT, advertisement.getAdId());
    }

    /**
     * Update publisher to Data Base.
     *
     * @param advertisement advertisement to update
     * @return new Advertisement
     */
    @Override
    public Advertisement update(final Advertisement advertisement) throws DaoException {
        jdbcTemplate.update(UPDATE_ADVERT, ps -> preparedStatementForUpdate(ps, advertisement));
        return jdbcTemplate.queryForObject(GET_ADVERT_BY_ID, BeanPropertyRowMapper.newInstance(Advertisement.class)
                , advertisement.getAdId());
    }

    /**
     * Method gets all advertisement from Data Base.
     *
     * @return list of all advertisements or empty otherwise
     */
    @Override
    public List<Advertisement> getAll() throws DaoException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_ALL_ADVERTS);
        return rows.stream().map(row -> {
            Advertisement advertisement = new Advertisement();
            advertisement.setAdId((Long) row.get("AD_ID"));
            advertisement.setTitle((String) row.get("TITLE"));
            advertisement.setContext((String) row.get("CONTEXT"));
            advertisement.setImageUrl((String) row.get("IMAGE_URL"));
            advertisement.setLanguage((String) row.get("LANGUAGE"));
            return advertisement;
        }).collect(Collectors.toList());
    }

    private void preparedStatementForUpdate(final PreparedStatement ps, final Advertisement advertisement) throws SQLException {
        ps.setString(1, advertisement.getTitle());
        ps.setString(2, advertisement.getContext());
        ps.setString(3, advertisement.getImageUrl());
        ps.setString(4, advertisement.getLanguage());
        ps.setLong(5, advertisement.getAdId());
    }
}
