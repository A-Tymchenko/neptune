package com.ra.airport.dto;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class TicketDTO {

    private Integer ticketId;

    @NotNull
    @Size(min = LengthHelper.MIN_T_NUM,
            max = LengthHelper.MAX_T_NUM,
            message = "Ticket Number should have 6-24 characters")
    private String ticketNumber;

    @NotNull
    @Size(min = LengthHelper.MIN_T_PASS_NAME,
            max = LengthHelper.MAX_T_PASS_NAME,
            message = "Passenger Name should have 2-64 characters")
    private String passengerName;

    @NotNull
    @Size(min = LengthHelper.MIN_T_DOC,
            max = LengthHelper.MAX_T_DOC,
            message = "Document should have 6-24 characters")
    private String document;

    @NotNull(message = "Selling Date is empty!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
