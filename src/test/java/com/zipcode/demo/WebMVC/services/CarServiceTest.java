package com.zipcode.demo.WebMVC.services;


import com.zipcode.demo.WebMVC.models.Car;
import com.zipcode.demo.WebMVC.repositories.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @Test
    @DisplayName("Test findById Success")
    public void tetFindByIdSuccess(){

        // Set up mock object and repository
        Car mockCar = new Car(1,"Koenigsegg", "CCXR Trevita", "Blue", 2020);
        doReturn(Optional.of(mockCar)).when(carRepository).findById(1L);

        // Execute call
        Optional<Car> returnCar = carService.findById(1);

        // Check assertions
        Assertions.assertTrue(returnCar.isPresent(), "No Car was found when there should be");
        Assertions.assertSame(returnCar.get(), mockCar, "Models dont match up");
    }

    @Test
    @DisplayName("Test findById Fail")
    public void tetFindByIdFail(){

        // Set up mock repository
        doReturn(Optional.empty()).when(carRepository).findById(1L);

        //execute the service call
        Optional<Car> returnCar = carService.findById(1);

        // Check assertions
        Assertions.assertFalse(returnCar.isPresent(), "Car was found, when it should't be");
    }

    @Test@DisplayName("Test findAll")
    public  void testFindAll(){
        // Setup mock objects and repo
        Car mockCar1 = new Car(1,"Koenigsegg", "CCXR Trevita", "Blue", 2020);
        Car mockCar2 = new Car(2,"Koenigsegg", "gemera", "Blue", 2020);
        doReturn(Arrays.asList(mockCar1, mockCar2)).when(carRepository).findAll();

        // Execute the service call
        List<Car> returnListCar = carService.findAll();

        // Check assertions
        Assertions.assertEquals(2, returnListCar.size(), "findAll should return 2 products");
    }

    @Test
    @DisplayName("Test save product")
    public void testSave(){
        Car mockCar = new Car(1,"Koenigsegg", "CCXR Trevita", "Blue", 2020);
        doReturn(mockCar).when(carRepository).save(any());

        Car returnCar = carService.save(mockCar);

        Assertions.assertNotNull(returnCar, "The saved product should not be null");
    }
}
