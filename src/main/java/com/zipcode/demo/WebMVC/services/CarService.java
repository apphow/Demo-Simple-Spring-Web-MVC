package com.zipcode.demo.WebMVC.services;


import com.zipcode.demo.WebMVC.models.Car;
import com.zipcode.demo.WebMVC.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarService {


    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<Car> findAll(){
        return this.carRepository.findAll();
    }

    public List<Car> findByMake(String make){
        return this.carRepository.findByMake(make);
    }
}
