package com.ra.shop.warehouse;

import com.ra.shop.wharehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ra.shop.warehouse.tools.Tools.creteWarehouse;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse firstWarehouse;
    private Warehouse secondWarehouse;

    @BeforeEach
    public void initWarehouse() {
        firstWarehouse = creteWarehouse();
        secondWarehouse = creteWarehouse();
    }

    @Test
    public void whenWarehousesTheSameEqualsReturnsTrue() {
        assertEquals(firstWarehouse.hashCode(), secondWarehouse.hashCode());

        assertEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenEqualsObjectsTrueExpected() {
        assertEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenDifferentObjectsFalseExpected() {
        assertNotEquals(firstWarehouse, Object.class);
    }

    @Test
    public void whenWarehousesDiffersEqualsReturnFalse() {
        firstWarehouse.setIdNumber(2);
        assertFalse(firstWarehouse.hashCode() == secondWarehouse.hashCode());

        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenToStringMessageMustBe() {
        String expected = "Warehouse{"
                + "idNumber=" + 1
                + ", name='" + "lola" + '\''
                + ", price=" + Double.MIN_VALUE
                + ", amount=" + 2
                + '}';
        assertEquals(firstWarehouse.toString(), expected);
    }

    @Test
    public void whenDifferentIdFalseIsExpected() {
        firstWarehouse.setIdNumber(0);
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenDifferentNamesFalseIsExpected() {
        firstWarehouse.setName("lelia");
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenDifferentPriceFalseIsExpected() {
        firstWarehouse.setPrice(1.1);
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenDifferentAmountFalseIsExpected() {
        firstWarehouse.setAmount(1);
        assertNotEquals(firstWarehouse, secondWarehouse);
    }

    @Test
    public void whenEqualsObjectsWithNullExpectedFalse() {
        assertNotEquals(firstWarehouse,null);
    }

    @Test
    public void whenEqualsWithOtherClasses() {
        assertEquals(firstWarehouse, firstWarehouse);
    }
}
