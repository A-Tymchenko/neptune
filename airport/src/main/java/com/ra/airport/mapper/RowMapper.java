package com.ra.airport.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper class helps map {@link ResultSet} to entity.
 * @param <T> entity to mapping
 */
public interface RowMapper<T> {

    /**
     * Map {@link ResultSet} to some entity.
     *
     * @param resultSet source {@link ResultSet}
     * @param entity target entity
     * @return entity with filled fields
     * @throws SQLException standard SQL exception
     */
    T mapRow(ResultSet resultSet, T entity) throws SQLException;
}

