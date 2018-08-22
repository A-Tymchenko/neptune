package com.ra.advertisement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedirectController {

    /**
     * This method redirects request into advertisementform.jsp page.
     * @return modelAndView
     */
    @RequestMapping(value = "/advertisements", method = RequestMethod.GET)
    public ModelAndView executeAdvertisement() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("advertisementform");
        return modelAndView;
    }

    /**
     * This method redirects request into deviceform.jsp page.
     * @return modelAndView
     */
    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public ModelAndView executeDevice() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deviceform");
        return modelAndView;
    }

    /**
     * This method redirects request into publisherform.jsp page.
     * @return modelAndView
     */
    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public ModelAndView executePublisher() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("publisherform");
        return modelAndView;
    }

    /**
     * This method redirects request into providerform.jsp page.
     * @return modelAndView
     */
    @RequestMapping(value = "/providers", method = RequestMethod.GET)
    public ModelAndView executeProvider() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("providerform");
        return modelAndView;
    }

    /**
     * This method redirects request into index.jsp page.
     * @return modelAndView
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
