package com.ra.airport.dto;

import java.sql.Timestamp;

public class TicketDTO {
    private Integer ticketId;
    private String ticketNumber;
    private String passengerName;
    private String document;
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
