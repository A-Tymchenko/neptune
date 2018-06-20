package com.ra.shop.service;

import com.ra.shop.daotest.Customer;
import com.ra.shop.model.Good;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class GoodDAO implements ShopDao<Good> {

    private Map<Integer, Good> idToGood = new HashMap<>();


    @Override
    public Stream<Good> getAll() throws Exception {
        return idToGood.values().stream();
    }

    @Override
    public Optional<Good> getById(int id) throws Exception {
        return Optional.ofNullable(idToGood.get(id));
    }

    @Override
    public boolean add(Good good) throws Exception {
        if (getById(good.getId()).isPresent()) {
            return false;
        }
        idToGood.put(good.getId(), good);
        return true;
    }

    @Override
    public boolean update(Good good) throws Exception {
        return idToGood.replace(good.getId(), good) != null;
    }

    @Override
    public boolean delete(Good good) throws Exception {
        return idToGood.remove(good.getId()) != null;
    }
}
