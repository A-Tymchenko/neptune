package com.ra.courses.airport.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    T mapRow(ResultSet resultSet, T entity) throws SQLException;
}
