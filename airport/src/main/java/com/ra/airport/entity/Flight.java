package com.ra.airport.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Flight entity correspond to flight table in DB.
 */
public class Flight {

   private Integer identifier;
   private String name;
   private String carrier;
   private LocalTime duration;
   private LocalDateTime departureDate;
   private LocalDateTime arrivalDate;
   private Double fare;
   private Boolean mealOn;

    public Flight() {}

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final Integer identifier) {
        this.identifier = identifier;
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

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(final LocalTime duration) {
        this.duration = duration;
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

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Flight flight = (Flight) object;
        return Objects.equals(identifier, flight.identifier)
                && Objects.equals(name, flight.name)
                && Objects.equals(carrier, flight.carrier)
                && Objects.equals(duration, flight.duration)
                && Objects.equals(departureDate, flight.departureDate)
                && Objects.equals(arrivalDate, flight.arrivalDate)
                && Objects.equals(fare, flight.fare)
                && Objects.equals(mealOn, flight.mealOn);
    }

    @Override
    public String toString() {
        return "Flight{"
                + "id=" + identifier
                + ", name='" + name + '\''
                + ", carrier='" + carrier + '\''
                + ", duration=" + duration
                + ", departureDate=" + departureDate
                + ", arrivalDate=" + arrivalDate
                + ", fare=" + fare
                + ", mealOn=" + mealOn
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, carrier, duration, departureDate, arrivalDate, fare, mealOn);
    }
}
