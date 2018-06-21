package com.ra.project.service;

import com.ra.project.exceptions.InvalidOrderIdException;
import com.ra.project.model.Order;
import com.ra.project.repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of IService interface.
 */
public class OrderServiceImpl implements IService<Order> {

    /**
     * Field orderRepository.
     */
    private IRepository<Order> orderIRepository;

    /**
     * Creates anOrderServiceImpl instance.
     * @param orderIRepository OrderRepository.
     */
    public OrderServiceImpl(IRepository<Order> orderIRepository) {
        this.orderIRepository = orderIRepository;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer create(Order entity) {
        Objects.requireNonNull(entity);
        return orderIRepository.create(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Optional<Order> get(Long entityId) throws InvalidOrderIdException {
        idCheck(entityId);
        return orderIRepository.get(entityId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer update(Order forUpdate) throws InvalidOrderIdException {
        Objects.requireNonNull(forUpdate);
        idCheck(forUpdate.getId());
        return orderIRepository.update(forUpdate);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Integer delete(Long entityId) throws InvalidOrderIdException {
        idCheck(entityId);
        return orderIRepository.delete(entityId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Order> getAll() {
        return orderIRepository.getAll();
    }

    /**
     * Method simply checks for correct order`s identifier value
     * @param entityId -id for check.
     * @throws InvalidOrderIdException if entityId less or equal to 0.
     */
    private void idCheck(Long entityId) throws InvalidOrderIdException {
        if (entityId <= 0) {
            throw new InvalidOrderIdException("Invalid id : " + entityId);
        }
    }
}
