package com.ra.advertisement.controller;

import java.util.List;

import com.ra.advertisement.dto.AdvertisementDto;
import com.ra.advertisement.dto.DeviceDto;
import com.ra.advertisement.dto.ProviderDto;
import com.ra.advertisement.dto.PublisherDto;
import com.ra.advertisement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SaveEntitiesController {

    private final transient ProjectService<AdvertisementDto> advrtService;
    private final transient ProjectService<DeviceDto> deviceService;
    private final transient ProjectService<ProviderDto> providerService;
    private final transient ProjectService<PublisherDto> publisherService;

    @Autowired
    public SaveEntitiesController(final ProjectService advertService, final ProjectService deviceService,
                                  final ProjectService providerService, final ProjectService publisherService) {
        this.advrtService = advertService;
        this.deviceService = deviceService;
        this.providerService = providerService;
        this.publisherService = publisherService;
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param advertisementDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @PostMapping("/advertisements")
    public ModelAndView saveAdvertisement(@ModelAttribute("advertisement") final AdvertisementDto advertisementDto) {
        final List<String> answer = advrtService.saveEntityService(advertisementDto);
        return new ModelAndView("advertisementform", "resultOne", answer);
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param deviceDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @PostMapping("/devices")
    public ModelAndView saveDevice(@ModelAttribute("device") final DeviceDto deviceDto) {
        final List<String> answer = deviceService.saveEntityService(deviceDto);
        return new ModelAndView("deviceform", "resultTwo", answer);
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param providerDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @PostMapping("/providers")
    public ModelAndView saveProvider(@ModelAttribute("provider") final ProviderDto providerDto) {
        final List<String> answer = providerService.saveEntityService(providerDto);
        return new ModelAndView("providerform", "resultThree", answer);
    }

    /**
     * This method gets info as to new Entity and using corresponding service validate and save it.
     * Then return an answer to the User.
     *
     * @param publisherDto AdvertisementDto
     * @return modelAndView with the answer
     */
    @PostMapping("/publishers")
    public ModelAndView savePublisherr(@ModelAttribute("publisher") final PublisherDto publisherDto) {
        final List<String> answer = publisherService.saveEntityService(publisherDto);
        return new ModelAndView("publisherform", "resultFour", answer);
    }
}
