package com.ra.airport.helper;

import java.sql.Timestamp;

import com.ra.airport.entity.Plane;
import com.ra.airport.entity.Ticket;

public abstract class DataCreationHelper {

    public static Ticket createTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTicketNumber("AA111-BB111");
        ticket.setPassengerName("John Dow");
        ticket.setDocument("QQ12345678QQ");
        ticket.setSellingDate(Timestamp.valueOf("2018-07-24 08:00:00"));
        return ticket;
    }

}
