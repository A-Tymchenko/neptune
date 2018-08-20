package com.ra.airport.controller;

import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: anbo
 * Date: 19.08.2018
 */
@Controller
public class FlightsController {

    @RequestMapping("/flights")
    public String flights (Model model) {
        String s = "hello!";
        return s;
    }

}
