package com.ra.advertisement.controller;

import java.util.List;

import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowAllEntitiesController {

    private final transient ProjectService advrtService;
    private final transient ProjectService deviceService;
    private final transient ProjectService providerService;
    private final transient ProjectService publisherService;

    @Autowired
    public ShowAllEntitiesController(final ProjectService advertService, final ProjectService deviceService,
                                     final ProjectService providerService, final ProjectService publisherService) {
        this.advrtService = advertService;
        this.deviceService = deviceService;
        this.providerService = providerService;
        this.publisherService = publisherService;
    }

    /**
     * This method puts the list of AdvertisementDto received by advertService method into the modelAndView.
     *
     * @return modelAndView
     */
    @GetMapping("/advertisements")
    public ModelAndView getAllAdvertisements() {
        final List<AdvertisementDto> list = advrtService.getAllEntityService();
        return new ModelAndView("alladvertisements", "alladvertisement", list);
    }

    /**
     * This method puts the list of DeviceDto received by devService method into the modelAndView.
     *
     * @return modelAndView
     */
    @GetMapping("/devices")
    public ModelAndView getAllDevices() {
        final List<DeviceDto> list = deviceService.getAllEntityService();
        return new ModelAndView("alldevices", "alldevices", list);
    }

    /**
     * This method puts the list of PublisherDto received by publService method into the modelAndView.
     *
     * @return modelAndView
     */
    @GetMapping("/publishers")
    public ModelAndView getAllPublishers() {
        final List<PublisherDto> list = publisherService.getAllEntityService();
        return new ModelAndView("allpublishers", "allpublishers", list);
    }

    /**
     * This method puts the list of ProviderDto received by provService method into the modelAndView.
     *
     * @return modelAndView
     */
    @GetMapping("/providers")
    public ModelAndView getAllProviders() {
        final List<ProviderDto> list = providerService.getAllEntityService();
        return new ModelAndView("allproviders", "allproviders", list);
    }
}
