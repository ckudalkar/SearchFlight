package com.myprojects.springboot.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FlightDto {

    @JsonIgnore
    private Long id;
    private String flightNum;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int price;

    @JsonIgnore
    private Duration duration;

    public FlightDto(long id,String flightNum, String origin, String destination, LocalDateTime departureTime,
                     LocalDateTime arrivalTime, int price) {
        this.id=id;
        this.flightNum=flightNum;
        this.origin=origin;
        this.destination=destination;
        this.departureTime=departureTime;
        this.arrivalTime=arrivalTime;
        this.price=price;
        this.duration=Duration.between(departureTime,arrivalTime);

    }
}
