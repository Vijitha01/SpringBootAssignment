package com.example.springassignment.controller;

import com.example.springassignment.dto.CarDto;
import com.example.springassignment.model.Car;
import com.example.springassignment.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTest {
    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }


    @Test
    @DisplayName("Verify GET Method for retrieving all cars")
    void shouldRetrieveAllCars() throws Exception {
        // Create some dummy phone data for testing
        List<CarDto> expectedCars = Arrays.asList(
                new CarDto(1L,"kiya",900000),
                new CarDto(2L,"Tokoyo",800000)
        );

        // Mock the phoneService to return the expected phones


        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/car/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the showPhones() method was called
        verify(carService, times(1)).getCars();
    }


    @Test
    @DisplayName("Verify POST Method for adding a car")
    void testAddCar() throws Exception {

        CarDto expectedCar = new CarDto(1L,"kiya",900000);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCar)));



        ArgumentCaptor<Car> carDtoCaptor = ArgumentCaptor.forClass(Car.class);


        verify(carService, times(1)).addNewCar(carDtoCaptor.capture());
        Car capturedCarDto = carDtoCaptor.getValue();

        assertEquals(expectedCar.getBrand(), capturedCarDto.getBrand());
        assertEquals(expectedCar.getPrice(), capturedCarDto.getPrice());
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Test DELETE /api/v1/car/{id}")
    void testDeletePhone() throws Exception {
        // Prepare test data
        Long id = 1L;

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/car/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the deletePhone() method was called with the correct id
        verify(carService, times(1)).deleteCar(id);
    }

    @Test
    @DisplayName("Test PUT /api/v1/car/{id}")
    void testUpdateCar() throws Exception {
        // Prepare test data
        Long id = 1L;
        String brand = "Updated Kiya";
        Integer price = 1000000;

        // Perform the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/car/{id}", id)
                        .param("brand", brand)
                        .param("price", String.valueOf(price))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the updatePhone() method was called with the correct parameters
        verify(carService, times(1)).updateCar(id, brand, price);
    }
}





