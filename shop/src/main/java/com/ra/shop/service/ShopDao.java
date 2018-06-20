package com.ra.shop.service;

import java.util.Optional;
import java.util.stream.Stream;

public interface ShopDao<T> {

    /**
     * @return all the model as a stream. The stream may be lazily or eagerly evaluated based
     * on the implementation. The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    Stream<T> getAll() throws Exception;

    /**
     * @param id unique identifier of the customer.
     * @return an optional with customer if a customer with unique identifier <code>id</code>
     * exists, empty optional otherwise.
     * @throws Exception if any error occurs.
     */
    Optional<T> getById(int id) throws Exception;

    /**
     * @param t the model to be added.
     * @return true if customer is successfully added, false if customer already exists.
     * @throws Exception if any error occurs.
     */
    boolean add(T t) throws Exception;

    /**
     * @param t the model to be updated.
     * @return true if customer exists and is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean update(T t) throws Exception;

    /**
     * @param t the model to be deleted.
     * @return true if customer exists and is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean delete(T t) throws Exception;
}

