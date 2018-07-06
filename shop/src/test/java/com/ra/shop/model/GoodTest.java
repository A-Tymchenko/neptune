package com.ra.shop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GoodTest {

    /**
     * Tests {@link Good}.
     */

    private Good good;
    private static final Long ID = 1l;
    private static final String NAME = "Winston";
    private static final Float PRICE = 54.6f;

    @BeforeEach
    public void setUp() {
        good = new Good(ID, NAME, PRICE);
    }

    @Test
    public void getAndSetId() {
        final long newId = 2;
        good.setId(newId);
        assertEquals(newId, good.getId(), 0.0002);
    }

    @Test
    public void getAndSetName() {
        final String newName = "Bill";
        good.setName(newName);
        assertEquals(newName, good.getName());
    }

    @Test
    public void getAndSetLastname() {
        final Float newPrice = 23.1f;
        good.setPrice(newPrice);
        assertEquals(newPrice, good.getPrice(), 0.0002);
    }

    @Test
    public void notEqualWithDifferentId() {
        final long newId = 2l;
        final Good otherGood = new Good(newId, NAME, PRICE);
        assertNotEquals(good, otherGood);
        assertNotEquals(good.hashCode(), otherGood.hashCode());
    }

    @Test
    public void equalsWithSameObjectValues() {
        final Good otherGood = new Good(ID, NAME, PRICE);
        assertEquals(good, otherGood);
        assertEquals(good.hashCode(), otherGood.hashCode());
    }

    @Test
    public void equalsWithSameObjects() {
        assertEquals(good, good);
        assertEquals(good.hashCode(), good.hashCode());
    }

    @Test
    public void testToString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("Good{")
                .append("id=" + good.getId())
                .append(", name='")
                .append(good.getName())
                .append("\', price=")
                .append(good.getPrice() + "}");
        assertEquals(buffer.toString(), good.toString());
    }
}