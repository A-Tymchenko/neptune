package com.ra.airport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    /**
     * Return path to index.jsp.
     * @return index
     */
    @RequestMapping("/")
    public String getIndex() {
        return "/index";
    }
}
