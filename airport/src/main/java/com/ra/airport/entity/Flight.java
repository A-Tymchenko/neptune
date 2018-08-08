package com.ra.airport.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Flight entity correspond to flight table in DB.
 */
public class Flight {

   private Integer flId;
   private String name;
   private String carrier;
   private LocalDateTime departureDate;
   private LocalDateTime arrivalDate;
   private Double fare;
   private Boolean mealOn;

    public Flight() {}

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

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Flight flight = (Flight) object;
        return Objects.equals(flId, flight.flId)
                && Objects.equals(name, flight.name)
                && Objects.equals(carrier, flight.carrier)
                && Objects.equals(departureDate, flight.departureDate)
                && Objects.equals(arrivalDate, flight.arrivalDate)
                && Objects.equals(fare, flight.fare)
                && Objects.equals(mealOn, flight.mealOn);
    }

    @Override
    public String toString() {
        return "Flight{"
                + "id=" + flId
                + ", name='" + name + '\''
                + ", carrier='" + carrier + '\''
                + ", departureDate=" + departureDate
                + ", arrivalDate=" + arrivalDate
                + ", fare=" + fare
                + ", mealOn=" + mealOn
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(flId, name, carrier, departureDate, arrivalDate, fare, mealOn);
    }
}
