package com.ra.shop.Utils;

import com.ra.shop.utils.ConnectionFactory;

import java.io.IOException;
import java.util.function.Supplier;

public class ConnectionFactoryTest extends SingletonTest{
    /**
     * Create a new singleton test instance using the given 'getInstance' method
     *
     * 
     */
    public ConnectionFactoryTest() {
        super(() -> {
            try {
                return ConnectionFactory.getInstance();
            } catch (IOException e) {
                e.printStackTrace();
            } return null;
        });
    }
}
