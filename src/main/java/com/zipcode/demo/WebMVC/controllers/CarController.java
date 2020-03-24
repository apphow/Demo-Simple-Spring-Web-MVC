package com.zipcode.demo.WebMVC.controllers;


import com.zipcode.demo.WebMVC.models.Car;
import com.zipcode.demo.WebMVC.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService){
       this.carService = carService;
    }

    @GetMapping("/all")
    public @ResponseBody List<Car> findAll(){
        System.out.println(carService.findAll());
        return carService.findAll();
    }

    @GetMapping("/make")
    public @ResponseBody
    List<Car> findByMake(@RequestParam String make){
        return carService.findByMake(make);
    }
}
