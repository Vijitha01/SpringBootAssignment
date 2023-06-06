package com.example.springassignment.repository;

import com.example.springassignment.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    @Query("SELECT c from Car c where c.brand=?1")
    Optional<Car> findCarByBrand(String brand);

}
