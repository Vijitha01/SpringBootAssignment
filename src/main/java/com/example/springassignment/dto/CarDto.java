package com.example.springassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String brand;
    private Integer price;
}
