package com.zipcode.demo.WebMVC.models;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String make;
    private String model;
    private String color;
    private long year;
    private String url;

    public Car(){}

    public Car(String make, String model, String color, long year) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public Car(long id, String make, String model, String color, long year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }
}
