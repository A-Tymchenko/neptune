package com.ra.airport.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Ticket {
    private Integer ticketId;
    private String ticketNumber;
    private String passengerName;
    private String document;
    private Timestamp sellingDate;

    public Ticket() {
    }

    public Ticket(final Integer ticketId, final String ticketNumber, final String passengerName,
                  final String document, final Timestamp sellingDate) {
        this.ticketId = ticketId;
        this.ticketNumber = ticketNumber;
        this.passengerName = passengerName;
        this.document = document;
        this.sellingDate = sellingDate;
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
        this.ticketNumber = ticketNumber.trim();
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(final String passengerName) {
        this.passengerName = passengerName.trim();
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(final String document) {
        this.document = document.trim();
    }

    public Timestamp getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(final Timestamp sellingDate) {
        this.sellingDate = sellingDate;
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "ticketId=" + ticketId
                + ", ticketNumber='" + ticketNumber + '\''
                + ", passengerName='" + passengerName + '\''
                + ", document='" + document + '\''
                + ", sellingDate=" + sellingDate
                + '}';
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Ticket ticket = (Ticket) obj;
        return Objects.equals(ticketId, ticket.ticketId)
                && Objects.equals(ticketNumber, ticket.ticketNumber)
                && Objects.equals(passengerName, ticket.passengerName)
                && Objects.equals(document, ticket.document)
                && Objects.equals(sellingDate, ticket.sellingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, ticketNumber, passengerName, document, sellingDate);
    }
}
