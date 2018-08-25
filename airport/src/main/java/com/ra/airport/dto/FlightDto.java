package com.ra.airport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Flight Dto object.
 */
public class FlightDto {
    
    private Integer flId;

    @NotBlank(message = "Please enter flight name.")
    private String name;

    @NotBlank(message = "Please enter carrier.")
    private String carrier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalDate;

    @NotNull
    private Double fare;

    @NotNull
    private Boolean mealOn;

    public FlightDto() {
    }

    public Integer getFlId() {
        return flId;
    }

    public void setFlId(final Integer flId) {
        this.flId = flId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(final String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(final LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(final Double fare) {
        this.fare = fare;
    }

    public Boolean getMealOn() {
        return mealOn;
    }

    public void setMealOn(final Boolean mealOn) {
        this.mealOn = mealOn;
    }
}