package com.ra.shop;

import com.ra.shop.config.AppConfiguration;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfiguration.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/create_table.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/drop_table.sql")
class GoodsRepositoryIntegrationTest {

    @Autowired
    GoodsRepositoryImpl dao;

    private static final Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
    private Long existingGoodsID;

    @BeforeEach
    public void createSchema() throws SQLException, IOException, RepositoryException {
        existingGoods.setId(1l);
        Goods result = dao.create(existingGoods);
        existingGoodsID = result.getId();
        assertTrue(result.equals(dao.get(result.getId())));
    }

    @Test
    public void addingShouldResultInSuccess() throws Exception {
        List<Goods> goods = dao.getAll();
        assumeTrue(goods.size() == 1);

        final Goods nonExistingGoods = new Goods("Parlament", 7622210722416l, 2.6F);
        nonExistingGoods.setId(0l);
        Goods result = dao.create(nonExistingGoods);
        assertTrue(result.equals(dao.get(result.getId())));

        assertGoodsCountIs(2);
        assertTrue(nonExistingGoods.equals(dao.get(nonExistingGoods.getId())));
    }

    @Test
    void deletionShouldBeFailureAndNotAffectExistingGoods() throws RepositoryException {
        final Goods nonExistingGoods = new Goods("Marlboro", 4050300003924l, 3.4f);
        nonExistingGoods.setId(getNonExistingGoodId());
        assertGoodsCountIs(1);
        assertFalse(dao.delete(nonExistingGoods.getId()));
        assertGoodsCountIs(1);
    }

    @Test
    void updationShouldBeFailureAndNotAffectExistingGoods() throws RepositoryException, IOException, SQLException {
        final Long nonExistingId = getNonExistingGoodId();
        final String newName = "Dunhill";
        final Long newBarcode = 4820005924653l;
        final Float newPrice = 12.6f;
        final Goods goods = new Goods(newName, newBarcode, newPrice);
        goods.setId(nonExistingId);
        assertThrows(RepositoryException.class, () -> {
            dao.get(dao.update(goods).getId());
        });
    }

    @Test
    void getShouldReturnNoGoods() throws RepositoryException {
        assertThrows(RepositoryException.class, () -> {
            dao.get(getNonExistingGoodId());
        });
    }

    @Test
    void addingShouldResultInFailureAndNotAffectExistingGoods() throws RepositoryException {
        Goods existingGoods = new Goods("Camel", 7622210609779l, 1.2f);
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
        final String newName = "L&M";
        final Long newBarcode = 740617152326l;
        final Float newPrice = 32.7f;
        final Goods goods = new Goods(newName, newBarcode, newPrice);
        goods.setId(existingGoods.getId());
        Goods result = dao.update(goods);

        assertTrue(goods.equals(result));

        final Goods goodsGet = dao.get(existingGoods.getId());
        assertEquals(newName, goodsGet.getName());
        assertEquals(newBarcode, goodsGet.getBarcode(), 0.0002);
        assertEquals(newPrice, goodsGet.getPrice(), 0.0002);
    }

    private void assertGoodsCountIs(int count) throws RepositoryException {
        List<Goods> allGoods = dao.getAll();
        assertTrue(allGoods.size() == count);
    }

    private Long getNonExistingGoodId() {
        return 999l;
    }
}
