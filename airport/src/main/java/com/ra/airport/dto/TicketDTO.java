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

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Timestamp getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(Timestamp sellingDate) {
        this.sellingDate = sellingDate;
    }
}
