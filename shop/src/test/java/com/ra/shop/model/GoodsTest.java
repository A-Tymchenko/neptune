package com.ra.shop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GoodsTest {

    /**
     * Tests {@link Goods}.
     */

    private Goods goods;
    private static final Long ID = 1l;
    private static final String NAME = "Winston";
    private static final Long BARCODE = 7622210609779l;
    private static final Float PRICE = 54.6f;

    @BeforeEach
    public void setUp() {
        goods = new Goods(ID, NAME, BARCODE, PRICE);
    }

    @Test
    public void getAndSetId() {
        final long newId = 2;
        goods.setId(newId);
        assertEquals(newId, goods.getId(), 0.0002);
    }

    @Test
    public void getAndSetName() {
        final String newName = "Bill";
        goods.setName(newName);
        assertEquals(newName, goods.getName());
    }

    @Test
    public void getAndSetBarcode() {
        final long newBarcode = 4050300003924l;
        goods.setId(newBarcode);
        assertEquals(newBarcode, goods.getId(), 0.0002);
    }

    @Test
    public void getAndSetLastname() {
        final Float newPrice = 23.1f;
        goods.setPrice(newPrice);
        assertEquals(newPrice, goods.getPrice(), 0.0002);
    }

    @Test
    public void notEqualWithDifferentId() {
        final long newId = 2l;
        final Goods otherGood = new Goods(newId, NAME, BARCODE, PRICE);
        assertNotEquals(goods, otherGood);
        assertNotEquals(goods.hashCode(), otherGood.hashCode());
    }

    @Test
    public void equalsWithSameObjectValues() {
        final Goods otherGood = new Goods(ID, NAME, BARCODE, PRICE);
        assertEquals(goods, otherGood);
        assertEquals(goods.hashCode(), otherGood.hashCode());
    }

    @Test
    public void equalsWithSameObjects() {
        assertEquals(goods, goods);
        assertEquals(goods.hashCode(), goods.hashCode());
    }

    @Test
    public void testToString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("Goods{")
                .append("id=" + goods.getId())
                .append(", name='")
                .append(goods.getName())
                .append("\', barcode=")
                .append(goods.getBarcode())
                .append(", price=")
                .append(goods.getPrice() + "}");
        assertEquals(buffer.toString(), goods.toString());
    }
}