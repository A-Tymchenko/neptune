package com.ra.shop;

import com.ra.shop.entity.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse firstWarehouse;
    private Warehouse secondWarehouse;

    @BeforeEach
    public void initWarehouse() {
        firstWarehouse = new Warehouse("Lola", Double.MIN_VALUE, 2);
        firstWarehouse.setIdNumber(1L);
        secondWarehouse = new Warehouse("Lola", Double.MIN_VALUE, 2);
        secondWarehouse.setIdNumber(1L);
    }

    /**
     * testing equals method with 2 equal objects.
     *
     * @return true
     */
    @Test
    public void whenWarehousesTheSameEqualsReturnsTrue() {
        assertEquals(firstWarehouse.hashCode(), secondWarehouse.hashCode());

        assertEquals(firstWarehouse, secondWarehouse);
    }

    /**
     * testing equals method with 2 different objects.
     *
     * @return false
     */
    @Test
    public void whenWarehousesDiffersEqualsReturnFalse() {
        firstWarehouse.setIdNumber(2L);
        assertFalse(firstWarehouse.hashCode() == secondWarehouse.hashCode());

        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    /**
     * testing toString method.
     *
     * @return correct string
     */
    @Test
    public void whenToStringMessageMustBe() {
        String expected = "Warehouse{"
                + "idNumber=" + 1
                + ", name='" + "Lola" + '\''
                + ", price=" + Double.MIN_VALUE
                + ", amount=" + 2
                + '}';
        assertEquals(firstWarehouse.toString(), expected);
    }

    /**
     * testing equals method with 2 equal objects.
     *
     * @return true
     */
    @Test
    public void whenEqualsObjectsTrueExpected() {
        assertTrue(firstWarehouse.equals(secondWarehouse));
    }

    /**
     * testing equals method with 2 different objects.
     *
     * @return false
     */
    @Test
    public void whenDifferentIdFalseIsExpected() {
        firstWarehouse.setIdNumber(2L);
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    /**
     * testing equals method with 2 different objects.
     *
     * @return false
     */
    @Test
    public void whenDifferentNamesFalseIsExpected() {
        firstWarehouse.setName("lelia");
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    /**
     * testing equals method with 2 different objects.
     *
     * @return false
     */
    @Test
    public void whenDifferentPriceFalseIsExpected() {
        firstWarehouse.setPrice(1.1);
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    /**
     * testing equals method with 2 different objects.
     *
     * @return false
     */
    @Test
    public void whenDifferentAmountFalseIsExpected() {
        firstWarehouse.setAmount(1);
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    /**
     * testing equals method with null.
     *
     * @return false
     */
    @Test
    public void whenEqualsObjectsWithNullExpectedFalse() {
        assertNotEquals(firstWarehouse,null);
    }

    /**
     * testing equals method with 2 different objects.
     *
     * @return false
     */
    @Test
    public void whenEqualsWithOtherClasses() {
        assertNotEquals(firstWarehouse, new Object());
    }
}
