package com.example.springassignment.service;

import com.example.springassignment.dto.CarDto;
import com.example.springassignment.model.Car;
import com.example.springassignment.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@Slf4j
class CarServiceTest {
    @Test
    @DisplayName("Displays the car details")
    void testShowAllCars(){
        CarRepository carRepository =mock(CarRepository.class);
        Car c1 = new Car("kiya",900000);
        Car c2 = new Car("lamborghini",120000);
        List<Car> cars= Arrays.asList(c1,c2);
        given(carRepository.findAll()).willReturn(cars);
        CarService carService=new CarService(carRepository);
        //when
        List<CarDto> carslist=carService.getCars()
                .stream()
                .map(car -> CarDto.builder()
                        .brand(car.getBrand())
                        .price(car.getPrice())
                        .id(car.getId())
                        .build())
                .toList();
        //then
        verify(carRepository, times(1)).findAll();
        Assertions.assertEquals(2,carslist.size());
       }
       @ParameterizedTest
       @CsvSource({
               "'kiya', 900000",                   // No changes
               "'kiya updated', 1000000",// Only price changed
       })
       @DisplayName("New Updates in car")
    void testUpdateCar(String CarName,Integer CarCost){
        CarRepository carRepository = mock(CarRepository.class);
        Car existingCar = new Car("kiya", 900000);
        Car updatedCar = new Car(CarName, CarCost);
        given(carRepository.findById(1L)).willReturn(Optional.of(existingCar));
        given(carRepository.save(any(Car.class))).willReturn(updatedCar);
        CarService carService = new CarService(carRepository);
           // when

           carService.updateCar(1L, updatedCar.getBrand(), updatedCar.getPrice());
           Assertions.assertThrows(IllegalStateException.class, () -> {
               carService.updateCar(2L, updatedCar.getBrand(), updatedCar.getPrice());
           });
           //then
           verify(carRepository, times(1)).findById(1L);

       }
    @Test
    @DisplayName("Adding new cars")
    void testAddCar() {
        // given
        CarRepository carRepository = mock(CarRepository.class);
        Car newCarDto = new Car(1L, "kiya",900000);
        CarService carService = new CarService(carRepository);
        // when
        carService.addNewCar(newCarDto);
        CarDto newCar = new CarDto(2L,"tokyo",600000);
        assertNotNull(newCar.toString());
        // then
        verify(carRepository, times(1)).save(any(Car.class));
    }
    @Test
    @DisplayName("Deleting a car")
    void testDeleteCar() {
        // given
        CarRepository carRepository = mock( CarRepository.class);
        CarService phoneService = new CarService(carRepository);
        // when
        phoneService.deleteCar(1L);
        // then
        verify(carRepository, times(1)).deleteById(1L);
    }
}
