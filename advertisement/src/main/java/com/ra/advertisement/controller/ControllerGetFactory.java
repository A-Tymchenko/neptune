package com.ra.advertisement.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.ra.advertisement.controller.get.GetAllAdvertController;
import com.ra.advertisement.controller.get.GetAllDeviceController;
import com.ra.advertisement.controller.get.GetAllProviderController;
import com.ra.advertisement.controller.get.GetAllPublisherController;
import com.ra.advertisement.controller.get.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("controlerGetFactory")
public class ControllerGetFactory implements ControllerFactory {
    private final transient Map<String, Controller> controllersMap = new HashMap<>();
    @Autowired
    private transient GetAllAdvertController getAllAContr;
    @Autowired
    private transient GetAllDeviceController getAllDContr;
    @Autowired
    private transient GetAllProviderController getAllPrContr;
    @Autowired
    private transient GetAllPublisherController getAllPubContr;
    @Autowired
    private transient IndexController indexController;

    /**
     * @param request HttpServletRequest
     * @return Get Controller from Map.
     */
    public Controller getController(final HttpServletRequest request) {
        if (controllersMap.isEmpty()) {
            controllersMap.put("alladvertisement", getAllAContr);
            controllersMap.put("alldevices", getAllDContr);
            controllersMap.put("allproviders", getAllPrContr);
            controllersMap.put("allpublishers", getAllPubContr);
            controllersMap.put("favicon.ico", indexController);
        }
        final String[] req = request.getRequestURI().split("/");
        return controllersMap.get(req[req.length - 1]);
    }
}