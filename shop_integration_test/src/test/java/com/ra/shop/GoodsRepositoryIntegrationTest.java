package com.ra.shop;

import com.ra.shop.config.AppConfiguration1;
import com.ra.shop.exceptions.RepositoryException;
import com.ra.shop.model.Goods;
import com.ra.shop.repository.implementation.GoodsRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfiguration1.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/create_table.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/drop_table.sql")
class GoodsRepositoryIntegrationTest {

    @Autowired
    GoodsRepositoryImpl dao;

    private static final Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2d);
    private Long existingGoodsID;

    @BeforeEach
    public void createSchema() throws RepositoryException {
        existingGoods.setId(1l);
        Goods result = dao.create(existingGoods);
        existingGoodsID = result.getId();
        assertTrue(result.equals(dao.get(result.getId())));
    }

    @Test
    public void addingShouldResultInSuccess() throws Exception {
        assertGoodsCountIs(1);
        final Goods nonExistingGoods = new Goods("Parlament", 7622210722416l, 2.6d);
        nonExistingGoods.setId(0l);
        Goods result = dao.create(nonExistingGoods);
        assertTrue(result.equals(dao.get(result.getId())));
        assertGoodsCountIs(2);
        assertTrue(nonExistingGoods.equals(dao.get(nonExistingGoods.getId())));
    }

    @Test
    void deletionShouldBeFailureAndNotAffectExistingGoods() throws RepositoryException {
        assertGoodsCountIs(1);
        assertFalse(dao.delete(getNonExistingGoodId()));
        assertGoodsCountIs(1);
    }

    @Test
    void updationShouldBeFailureAndNotAffectExistingGoods() {
        final Goods goods = new Goods("Dunhill", 4820005924653l, 12.6d);
        goods.setId(getNonExistingGoodId());
        assertThrows(RepositoryException.class, () -> {
            dao.get(dao.update(goods).getId());
        });
    }

    @Test
    void getShouldReturnNoGoods() {
        assertThrows(RepositoryException.class, () -> {
            dao.get(getNonExistingGoodId());
        });
    }

    @Test
    void addingShouldResultInFailureAndNotAffectExistingGoods() throws RepositoryException {
        Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2d);
        existingGoods.setId(existingGoodsID);
        Goods result = dao.create(existingGoods);
        assertTrue(result.equals(existingGoods));
        assertGoodsCountIs(2);
        assertEquals(existingGoods, dao.get(existingGoods.getId()));
    }

    @Test
    void deletionShouldBeSuccessAndGoodsShouldBeNonAccessible() throws RepositoryException {
        boolean result = dao.delete(existingGoods.getId());
        assertEquals(true, result);
        assertGoodsCountIs(0);
        assertThrows(RepositoryException.class, () -> {
            dao.get(existingGoods.getId());
        });
    }

    @Test
    void updationShouldBeSuccessAndAccessingTheSameGoodsShouldReturnUpdatedInformation() throws RepositoryException {
        final Goods goods = new Goods("L&M", 740617152326l, 32.7d);
        goods.setId(existingGoods.getId());
        Goods result = dao.update(goods);
        assertTrue(goods.equals(result));
        assertTrue(goods.equals(dao.get(existingGoods.getId())));
    }

    private void assertGoodsCountIs(int count) throws RepositoryException {
        List<Goods> allGoods = dao.getAll();
        assertTrue(allGoods.size() == count);
    }

    private Long getNonExistingGoodId() {
        return 999l;
    }
}
