package com.ra.courses.airport.dao.impl;
import com.ra.courses.airport.entity.Plane;

import java.sql.ResultSet;
import java.sql.SQLException;



public class PlaneRowMapper implements RowMapper<Plane> {

    private static final String PLANE_ID = "id";
    private static final String OWNER = "owner";
    private static final String TYPE = "type";
    private static final String MODEL = "model";
    private static final String PLATNUMBER = "platenumber";


    /**
      * Map {@link ResultSet} to {@link Plane} instance.
      *
      * @param resultSet source {@link ResultSet}
      * @param plane target {@link Plane} entity
      * @return {@link Plane} with filled fields
      * @throws SQLException standard SQL exception
      */
            @Override
    public Plane mapRow(final ResultSet resultSet, final Plane plane) throws SQLException {
                plane.setId(resultSet.getInt(PLANE_ID));
                plane.setOwner(resultSet.getString(OWNER));
                plane.setType(resultSet.getString(TYPE));
                plane.setModel(resultSet.getString(MODEL));
                plane.setPlatenumber(resultSet.getInt(PLATNUMBER));

                return plane;
            }
}