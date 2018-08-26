package com.ra.advertisement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedirectController {

    /**
     * This method redirects request into advertisementform.jsp page.
     *
     * @return modelAndView
     */
    @GetMapping("/showadvertisementsform")
    public ModelAndView executeAdvertisement() {
        return new ModelAndView("advertisementform");
    }

    /**
     * This method redirects request into deviceform.jsp page.
     *
     * @return modelAndView
     */
    @GetMapping("/showdevicesform")
    public ModelAndView executeDevice() {
        return new ModelAndView("deviceform");
    }

    /**
     * This method redirects request into publisherform.jsp page.
     *
     * @return modelAndView
     */
    @GetMapping("/showpublishersform")
    public ModelAndView executePublisher() {
        return new ModelAndView("publisherform");
    }

    /**
     * This method redirects request into providerform.jsp page.
     *
     * @return modelAndView
     */
    @GetMapping("/showprovidersform")
    public ModelAndView executeProvider() {
        return new ModelAndView("providerform");
    }

    /**
     * This method redirects request into index.jsp page.
     *
     * @return modelAndView
     */
    @GetMapping("/")
    public ModelAndView getHomePage() {
        return new ModelAndView("index");
    }
}
