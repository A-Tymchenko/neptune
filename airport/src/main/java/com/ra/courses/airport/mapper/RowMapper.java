package com.ra.courses.airport.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    /**
     * Map {@link ResultSet} to some entity.
     * @param resultSet
     * @param entity
     * @return
     * @throws SQLException
     */
    T mapRow(ResultSet resultSet, T entity) throws SQLException;
}
