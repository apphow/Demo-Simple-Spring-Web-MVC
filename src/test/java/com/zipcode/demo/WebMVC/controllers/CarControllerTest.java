package com.zipcode.demo.WebMVC.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zipcode.demo.WebMVC.models.Car;
import com.zipcode.demo.WebMVC.services.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @MockBean
    private CarService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /car/1 - Found")
    void testGetProjectByIdFound() throws Exception{
        // Setup our mocked service
        Car mockCar = new Car(1,"Chevrolet", "Stingray", "Blade Silver Metallic", 2020);
        doReturn(Optional.of(mockCar)).when(service).findById(1);

        // Execute the GET request
        mockMvc.perform(get("/car/{id}", 1))

                //Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Chevrolet")))
                .andExpect(jsonPath("$.model", is("Stingray")))
                .andExpect(jsonPath("$.color", is("Blade Silver Metallic")))
                .andExpect(jsonPath("$.year", is(2020)));
    }

    @Test
    @DisplayName("GET  /car/1 - Not Found")
    void testGetCarByIdNotFound() throws Exception {
        //Setup our mocked service
        doReturn(Optional.empty()).when(service).findById(1);

        // Execute the Get request
        mockMvc.perform(get("/car/{id}", 1))

            //Validate that we get a 404 Not Found response
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /car - Success")
    void testCreateCar() throws Exception {
        // Setup mocked service
        Car postCar = new Car("Chevrolet", "Stingray", "Blade Silver Metallic", 2020);
        Car mockCar = new Car(1, "Chevrolet", "Stingray", "Blade Silver Metallic", 2020);
        doReturn(mockCar).when(service).save(any());

        mockMvc.perform(post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postCar)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/car/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Chevrolet")))
                .andExpect(jsonPath("$.model", is("Stingray")))
                .andExpect(jsonPath("$.color", is("Blade Silver Metallic")))
                .andExpect(jsonPath("$.year", is(2020)));
    }

    @Test
    @DisplayName("PUT /car/1 - Success")
    void testCarPutSuccess() throws Exception {
        // Setup mocked service
        Car putCar = new Car("Chevrolet", "Stingray", "Blade Silver Metallic", 2020);
        Car mockCar = new Car(1, "Chevrolet", "Stingray", "Blade Silver Metallic", 2020);
        doReturn(Optional.of(mockCar)).when(service).findById(1);
        doReturn(mockCar).when(service).save(any());

        mockMvc.perform(put("/car/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putCar)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.LOCATION, "/car/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Chevrolet")))
                .andExpect(jsonPath("$.model", is("Stingray")))
                .andExpect(jsonPath("$.color", is("Blade Silver Metallic")))
                .andExpect(jsonPath("$.year", is(2020)));
    }

    @Test
    @DisplayName("PUT /car/1 - Not Found")
    void testCarPutNotFound() throws Exception {
        // Setup mocked service
        Car putCar = new Car("Chevrolet", "Stingray", "Blade Silver Metallic", 2020);
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(put("/car/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putCar)))

                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }


    // Delete Test here
    @Test
    @DisplayName("DELETE /car/1 - Success")
    void testCarDeleteSuccess() throws Exception {
        // Setup mocked car
        Car mockCar = new Car("Chevrolet", "Stingray", "Blade Silver", 2020);

        // Setup the mocked service
        doReturn(Optional.of(mockCar)).when(service).findById(1);
        doReturn(true).when(service).delete(1);

        // Execute our DELETE request
        mockMvc.perform(delete("/car/{id}", 1))
                .andExpect(status().isOk());
    }





    static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}