package com.ra.airport.dto;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class TicketDTO {

    private Integer ticketId;

    @NotNull
    @Size(min = 6, max = 24, message = "Ticket Number should have 6-24 characters")
    private String ticketNumber;

    @NotNull
    @Size(min = 2, max = 64, message = "Passenger Name should have 2-64 characters")
    private String passengerName;

    @NotNull
    @Size(min = 6, max = 24, message = "Document should have 6-24 characters")
    private String document;

    @NotNull(message = "Selling Date is empty!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Timestamp sellingDate;

    public TicketDTO() {
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(final Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(final String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(final String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(final String document) {
        this.document = document;
    }

    public Timestamp getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(final Timestamp sellingDate) {
        this.sellingDate = sellingDate;
    }
}
