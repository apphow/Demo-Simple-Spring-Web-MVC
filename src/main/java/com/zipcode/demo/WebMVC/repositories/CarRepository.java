package com.zipcode.demo.WebMVC.repositories;

import com.zipcode.demo.WebMVC.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAll();

    List<Car> findByMake(String make);


}
