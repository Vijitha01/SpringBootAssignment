package com.example.springassignment.controller;

import com.example.springassignment.model.Car;
import com.example.springassignment.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/car")
public class CarController {
    private final CarService carService;
    @Autowired
    public CarController(CarService carService){
        this.carService=carService;
    }
    @GetMapping("/")
    public List<Car> getCars(){
return carService.getCars();
    }
    @PostMapping("/")
    public String registerNewCar(@RequestBody Car car){
        carService.addNewCar(car);
        return "New Car Details are Added";
    }
    @DeleteMapping(path = "{carId}")
    public String deleteCar(@PathVariable("carId") Long carId){
        carService.deleteCar(carId);
        return "Car Deleted";
    }
    @PutMapping(path="{carId}")
    public String updateCar(
            @PathVariable("carId") Long carId,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer price
    ) {
        carService.updateCar(carId, brand,price);
        return "Car Data is updated";
    }
}
