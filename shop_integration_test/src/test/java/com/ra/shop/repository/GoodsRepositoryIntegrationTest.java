package com.ra.shop.repository;

import com.ra.shop.config.ShopConfiguration;
import com.ra.shop.model.Goods;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ShopConfiguration.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/create_table.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/drop_table.sql")
class GoodsRepositoryIntegrationTest {

    @Autowired
    GoodsRepositoryImpl dao;

    private static final Goods TEST_GOODS = new Goods("Camel", 7622210609779l, 1.2d);
    private static final Goods TEST_GOODS_UPDATE = new Goods("Parlament", 7622210722416l, 2.6d);

    @Test
    public void whenCreatedGoodEqualsGoods() throws Exception {

        assertTrue(dao.create(TEST_GOODS).equals(TEST_GOODS));
    }

    @Test
    public void whenGetGoodsEqualsCreatedGoods () throws Exception {

        assertTrue(dao.create(TEST_GOODS).equals(dao.get(TEST_GOODS.getId())));
    }

    @Test
    public void whenDeletingCreatedGoodsReturnTrue() throws Exception {

        assertTrue(dao.delete((dao.create(TEST_GOODS)).getId()));
    }

    @Test
    public void whenUpdatingCreatedGoodsEqualsUpdatingGoods() throws Exception {
        TEST_GOODS_UPDATE.setId(dao.create(TEST_GOODS).getId());

        assertTrue(dao.update(TEST_GOODS_UPDATE).equals(dao.get(TEST_GOODS_UPDATE.getId())));
    }

    @Test
    public void whenReadAllGoodsReturnEmptyList () throws Exception {
        dao.create(TEST_GOODS).getId();

        assertFalse(dao.getAll().isEmpty());
    }
}
