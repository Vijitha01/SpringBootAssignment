package com.example.springassignment.configuration;

import com.example.springassignment.model.Car;
import com.example.springassignment.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CarConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            CarRepository repository
    ){
        return args -> {
            Car ford = new Car(
                    "Ford",
                    100000);
            Car ferrari = new Car(
                    "Ferrari",
                    500000);
            repository.saveAll(List.of(ford,ferrari));
        };
    }
}
