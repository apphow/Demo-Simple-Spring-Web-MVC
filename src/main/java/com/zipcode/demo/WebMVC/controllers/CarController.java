package com.zipcode.demo.WebMVC.controllers;


import com.zipcode.demo.WebMVC.models.Car;
import com.zipcode.demo.WebMVC.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService){
       this.carService = carService;
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return  this.carService.findById(id)
                    .map(car -> ResponseEntity
                                    .ok()
                                    .body(car))
                    .orElse(ResponseEntity
                                .notFound()
                                .build());
    }

    @GetMapping("/all")
    public List<Car> findAll(){
        return carService.findAll();
    }

    @GetMapping("/make")
    public List<Car> findByMake(@RequestParam String make){
        return carService.findByMake(make);
    }

    @PostMapping("/car")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        Car newCar = carService.save(car);

        try {
            return ResponseEntity
                    .created(new URI("/car/" + newCar.getId()))
                    .body(newCar);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value="/car/{id}")
    public ResponseEntity<?> updateCar(@RequestBody Car car,
                                       @PathVariable long id){
        Optional<Car> existingCar = carService.findById(id);

        return existingCar
                .map(c -> {
                    c.setMake(car.getMake());
                    c.setModel(car.getModel());
                    c.setColor(car.getColor());
                    c.setYear(car.getYear());

                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/car/" + c.getId()))
                                .body(c);
                    }catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    // Delete controller
    @DeleteMapping("/car/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        Optional<Car> existingCar = carService.findById(id);

        return existingCar
                .map(c -> {
                    carService.delete(c.getId());
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
