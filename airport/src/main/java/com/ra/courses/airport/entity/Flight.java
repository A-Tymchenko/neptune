package com.ra.courses.airport.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by anbo06131 on 6/15/2018.
 */
public class Flight {

   private Integer id;
   private String name;
   private String carrier;
   private LocalTime duration;
   private LocalDateTime departureDate;
   private LocalDateTime arrivalDate;
   private Double fare;
   private Boolean mealOn;

    public Flight() {
        
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departure_date) {
        this.departureDate = departure_date;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrival_date) {
        this.arrivalDate = arrival_date;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Boolean isMealOn() {
        return mealOn;
    }

    public void setMealOn(Boolean mealOn) {
        this.mealOn = mealOn;
    }

    @Override
   public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flight)) {
            return false;
        }
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", carrier='" + carrier + '\'' +
                ", duration=" + duration +
                ", departure_date=" + departureDate +
                ", arrival_date=" + arrivalDate +
                ", fare=" + fare +
                ", mealOn=" + mealOn +
                '}';
    }
}
