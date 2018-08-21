package com.ra.airport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    /**
     * Register bean for DataSourceInitializer.
     * @return index
     */
    @RequestMapping("/")
    public String index() {
        return "/index";
    }
}
