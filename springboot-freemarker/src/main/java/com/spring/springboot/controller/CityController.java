package com.spring.springboot.controller;

import com.spring.springboot.service.CityService;
import com.spring.springboot.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 城市 Controller 实现 Restful HTTP 服务
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Controller
@RequestMapping("/frmkr")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
    public String findOneCity(Model model, @PathVariable("id") Long id) {
        model.addAttribute("city", cityService.findCityById(id));
        return "city";
    }

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public String findAllCity(Model model) {
        List<City> cityList = cityService.findAllCity();
        model.addAttribute("cityList",cityList);
        return "cityList";
    }

    @RequestMapping(value = "/api/mycity", method = RequestMethod.GET)
    @ResponseBody
    public String findAllmyCity(Model model){
        return "myCity List";
    }

    @RequestMapping(value = "/default")
    @ResponseBody
    public String defaultPage(){
        System.out.println("\ndefaultPage defaultPage defaultPage\n");
        return "(springboot-freemarker CityController) Default Page";
    }

}
