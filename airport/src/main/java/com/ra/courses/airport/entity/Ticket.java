package com.ra.courses.airport.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Ticket {
    private Long idTicket;
    private String ticketNumber;
    private String passengerName;
    private String document;
    private Timestamp sellingDate;

    public Ticket() {
    }

    public Ticket(final String ticketNumber, final String passengerName, final String document,
                  final Timestamp sellingDate) {
        this.ticketNumber = ticketNumber.trim();
        this.passengerName = passengerName.trim();
        this.document = document.trim();
        this.sellingDate = sellingDate;
    }

    public Ticket(final Long idTicket, final String ticketNumber, final String passengerName, final String document,
                  final Timestamp sellingDate) {
        this.idTicket = idTicket;
        this.ticketNumber = ticketNumber.trim();
        this.passengerName = passengerName.trim();
        this.document = document.trim();
        this.sellingDate = sellingDate;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(final Long idTicket) {
        this.idTicket = idTicket;
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
        return "entities.Ticket{"
                + "idTicket=" + idTicket
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
        return Objects.equals(idTicket, ticket.idTicket)
                && Objects.equals(ticketNumber, ticket.ticketNumber)
                && Objects.equals(passengerName, ticket.passengerName)
                && Objects.equals(document, ticket.document)
                && Objects.equals(sellingDate, ticket.sellingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, ticketNumber, passengerName, document, sellingDate);
    }
}
