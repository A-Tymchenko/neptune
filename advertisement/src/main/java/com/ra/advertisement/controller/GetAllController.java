package com.ra.advertisement.controller;

import java.util.List;

import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SuppressWarnings("PMD")
public class GetAllController {

    @Autowired
    @Qualifier("advertService")
    private final transient AdvertisementService advertService = null;
    @Autowired
    @Qualifier("deviceService")
    private final transient AdvertisementService devService = null;
    @Autowired
    @Qualifier("providerService")
    private final transient AdvertisementService provService = null;
    @Autowired
    @Qualifier("publisherService")
    private final transient AdvertisementService publService = null;

    /**
     * This method puts the list of AdvertisementDto received by advertService method into the modelAndView.
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/alladvertisementget", method = RequestMethod.GET)
    public ModelAndView getAllAdvertisements() {
        final ModelAndView modelAndView = new ModelAndView();
        final List<AdvertisementDto> list = advertService.getAllEntityService();
        modelAndView.addObject("alladvertisement", list);
        modelAndView.setViewName("alladvertisement");
        return modelAndView;
    }

    /**
     * This method puts the list of DeviceDto received by devService method into the modelAndView.
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/alldevicesget", method = RequestMethod.GET)
    public ModelAndView getAllDevices() {
        final ModelAndView modelAndView = new ModelAndView();
        final List<DeviceDto> list = devService.getAllEntityService();
        modelAndView.addObject("alldevices", list);
        modelAndView.setViewName("alldevices");
        return modelAndView;
    }

    /**
     * This method puts the list of PublisherDto received by publService method into the modelAndView.
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/allpublishersget", method = RequestMethod.GET)
    public ModelAndView getAllPublishers() {
        final ModelAndView modelAndView = new ModelAndView();
        final List<PublisherDto> list = publService.getAllEntityService();
        modelAndView.addObject("allpublishers", list);
        modelAndView.setViewName("allpublishers");
        return modelAndView;
    }

    /**
     * This method puts the list of ProviderDto received by provService method into the modelAndView.
     *
     * @return modelAndView
     */
    @RequestMapping(value = "/allprovidersget", method = RequestMethod.GET)
    public ModelAndView getAllProviders() {
        final ModelAndView modelAndView = new ModelAndView();
        final List<ProviderDto> list = provService.getAllEntityService();
        modelAndView.addObject("allproviders", list);
        modelAndView.setViewName("allproviders");
        return modelAndView;
    }
}
