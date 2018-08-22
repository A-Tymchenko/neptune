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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SuppressWarnings("PMD")
public class SaveObjectsController {

    @Autowired
    @Qualifier("advertService")
    private final transient AdvertisementService advertService = null;
    @Autowired
    @Qualifier("deviceService")
    private final transient AdvertisementService devService = null;
    @Autowired
    @Qualifier("providerService")
    private final transient AdvertisementService providService = null;
    @Autowired
    @Qualifier("publisherService")
    private final transient AdvertisementService publService = null;

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param advertisementDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @RequestMapping(value = "/advertisementform", method = RequestMethod.POST)
    public ModelAndView saveAdvertisement(@ModelAttribute("advertisement") final AdvertisementDto advertisementDto) {
        final ModelAndView modelAndView = new ModelAndView();
        final List<String> answer = advertService.saveEntityService(advertisementDto);
        modelAndView.addObject("resultOne", answer);
        modelAndView.setViewName("advertisementform");
        return modelAndView;
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param deviceDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @RequestMapping(value = "/deviceform", method = RequestMethod.POST)
    public ModelAndView saveDevice(@ModelAttribute("device") final DeviceDto deviceDto) {
        final ModelAndView modelAndView = new ModelAndView();
        final List<String> answer = devService.saveEntityService(deviceDto);
        modelAndView.addObject("resultTwo", answer);
        modelAndView.setViewName("deviceform");
        return modelAndView;
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param providerDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @RequestMapping(value = "/providerform", method = RequestMethod.POST)
    public ModelAndView saveProvider(@ModelAttribute("provider") final ProviderDto providerDto) {
        final ModelAndView modelAndView = new ModelAndView();
        final List<String> answer = providService.saveEntityService(providerDto);
        modelAndView.addObject("resultThree", answer);
        modelAndView.setViewName("providerform");
        return modelAndView;
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param publisherDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @RequestMapping(value = "/publisherform", method = RequestMethod.POST)
    public ModelAndView savePublisherr(@ModelAttribute("publisher") final PublisherDto publisherDto) {
        final ModelAndView modelAndView = new ModelAndView();
        final List<String> answer = publService.saveEntityService(publisherDto);
        modelAndView.addObject("resultFour", answer);
        modelAndView.setViewName("publisherform");
        return modelAndView;
    }
}
