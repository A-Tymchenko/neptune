package com.ra.shop.warehouse;

import com.ra.shop.wharehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.ra.shop.warehouse.integration.Tools.creteWarehouse;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(firstWarehouse.hashCode() == secondWarehouse.hashCode());

        assertTrue(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenWarehousesDiffersEqualsReturnFalse() {
        firstWarehouse.setIdNumber(2);
        assertFalse(firstWarehouse.hashCode() == secondWarehouse.hashCode());

        assertFalse(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenToStringMessageMustBe() {
        String expected = "Warehouse{"
                + "idNumber=" + 1
                + ", name='" + "lola" + '\''
                + ", price=" + Double.MIN_VALUE
                + ", amount=" + 2
                + '}';
        assertTrue(firstWarehouse.toString().equals(expected));
    }

    @Test
    public void whenEqualsObjectsTrueExpected() {
        assertTrue(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenDifferentIdFalseIsExpected() {
        firstWarehouse.setIdNumber(2);
        assertFalse(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenDifferentNamesFalseIsExpected() {
        firstWarehouse.setName("lelia");
        assertFalse(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenDifferentPriceFalseIsExpected() {
        firstWarehouse.setPrice(1.1);
        assertFalse(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenDifferentAmountFalseIsExpected() {
        firstWarehouse.setAmount(1);
        assertFalse(firstWarehouse.equals(secondWarehouse));
    }

    @Test
    public void whenEqualsObjectsWithNullExpectedFalse() {
        assertFalse(firstWarehouse.equals(null));
    }

    @Test
    public void whenEqualsWithOtherClasses() {
        assertFalse(firstWarehouse.equals(new Object()));
    }
}
