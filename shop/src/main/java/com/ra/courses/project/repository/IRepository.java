package com.ra.courses.project.repository;

public interface IRepository<T> {

    /**
     * Method creates new entity.
     * @param entity that will be created.
     * @return T
     */
    T create(T entity);

    /**
     * Method returns entity from database.
     * @param entityId of the searched entity.
     * @return T
     */
    T get(int entityId);

    /**
     * Method returns update entity.
     * @param entityId of entity, that should be updated.
     * @param newEntity updated version of entity.
     * @return T
     */
    T update(int entityId, T newEntity);

    /**
     * Method will delete entity from the database.
     * @param entityId of entity that will be deleted.
     * @return Boolean.TRUE if entity deleted or Boolean.FALSE if not.
     */
    Boolean delete(int entityId);

}
