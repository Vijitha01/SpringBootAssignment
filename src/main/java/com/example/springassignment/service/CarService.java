package com.example.springassignment.service;

import com.example.springassignment.model.Car;
import com.example.springassignment.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public void addNewCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }
    @Transactional
    public void updateCar(Long carId, String brand, Integer price) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalStateException(
                        "Car with id " + carId + "does not exists"
                ));
        if (!Objects.equals(car.getPrice(), price)) {
            car.setPrice(price);
        }
        if (!Objects.equals(car.getBrand(), brand)) {
            car.setBrand(brand);
        }

    }
}


