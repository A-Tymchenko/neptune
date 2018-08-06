package com.ra.advertisement.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdvertisementService<T, E> {
    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity with genereted Id
     */
    List<String> saveEntityService(HttpServletRequest request);

    /**
     * Method get all entities from Db and transfer it into dtoObject.
     *
     * @return list of dto objects
     */
    List<T> getAllEntityService();

}
