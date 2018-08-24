package com.ra.advertisement.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("allservice")
public interface ProjectService<T> {
    /**
     * Method get dto and save entity int into Data Base and return Entity with id.
     *
     * @return entity with genereted Id
     */
    List<String> saveEntityService(T dto);

    /**
     * Method get all entities from Db and transfer it into dtoObject.
     *
     * @return list of dto objects
     */
    List<T> getAllEntityService();

}
