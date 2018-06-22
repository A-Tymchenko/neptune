package com.ra.airport.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Flight entity correspond to flight table in DB
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id) &&
                Objects.equals(name, flight.name) &&
                Objects.equals(carrier, flight.carrier) &&
                Objects.equals(duration, flight.duration) &&
                Objects.equals(departureDate, flight.departureDate) &&
                Objects.equals(arrivalDate, flight.arrivalDate) &&
                Objects.equals(fare, flight.fare) &&
                Objects.equals(mealOn, flight.mealOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, carrier, duration, departureDate, arrivalDate, fare, mealOn);
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
