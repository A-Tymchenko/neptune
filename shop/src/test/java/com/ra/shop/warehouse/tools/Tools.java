package com.ra.shop.warehouse.tools;

import com.ra.shop.wharehouse.Warehouse;

public class Tools {

    public static Warehouse creteWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setIdNumber(1);
        warehouse.setName("lola");
        warehouse.setPrice(Double.MIN_VALUE);
        warehouse.setAmount(2);
        return warehouse;
    }


}
