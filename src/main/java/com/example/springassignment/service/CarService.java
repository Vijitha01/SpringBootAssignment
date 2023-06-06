package com.example.springassignment.service;

import com.example.springassignment.model.Car;
import com.example.springassignment.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Car> carOptional = carRepository
                .findCarByBrand(car.getBrand());
        if (carOptional.isPresent()) {
            throw new IllegalStateException("New Brand car added");
        }
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
        if (price != null &&
                price > 0 && !Objects.equals(car.getPrice(), price)) {
            car.setPrice(price);
        }
        if (brand != null && brand.length() > 0 &&
                !Objects.equals(car.getBrand(), brand)) {
            Optional<Car> carOptional = carRepository.findCarByBrand(brand);
            if (carOptional.isPresent()) {
                throw new IllegalStateException("car updated");
            }
            car.setBrand(brand);
        }

    }
}


