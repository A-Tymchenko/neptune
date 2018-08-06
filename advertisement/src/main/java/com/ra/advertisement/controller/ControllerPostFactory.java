package com.ra.advertisement.controller;

import com.ra.advertisement.controller.post.AdvertSaveController;
import com.ra.advertisement.controller.post.DeviceSaveController;
import com.ra.advertisement.controller.post.ProviderSaveController;
import com.ra.advertisement.controller.post.PublisherSaveController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component("controlerPostFactory")
public final class ControllerPostFactory implements ControllerFactory {
    private final transient Map<String, Controller> controllersMap = new HashMap<>();
    @Autowired
    private transient AdvertSaveController advSaveController;
    @Autowired
    private transient DeviceSaveController devSaveController;
    @Autowired
    private transient ProviderSaveController prSaveController;
    @Autowired
    private transient PublisherSaveController pubSaveController;

    /**
     * @param request HttpServletRequest
     * @return Post Controller from Map.
     */
    @Override
    public Controller getController(final HttpServletRequest request) {
        if (controllersMap.isEmpty()) {
            controllersMap.put("saveAdvert", advSaveController);
            controllersMap.put("saveDevice", devSaveController);
            controllersMap.put("saveProvider", prSaveController);
            controllersMap.put("savePublisher", pubSaveController);
        }
        final String reqestParameter = request.getParameter("saveEntity");
        return controllersMap.get(reqestParameter);
    }
}
