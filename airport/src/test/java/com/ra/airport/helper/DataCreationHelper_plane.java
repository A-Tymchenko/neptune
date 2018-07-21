package com.ra.airport;

import com.ra.airport.entity.Plane;

public abstract class DataCreationHelper {
    private static final String SPACE = " ";

    public static Plane createPlane(){
        Plane plane=new Plane();
        plane.setId(1);
        plane.setPlatenumber(2);
        plane.setModel(SPACE);
        plane.setType(SPACE);
        plane.setOwner(SPACE);
        return plane;
    }

}
