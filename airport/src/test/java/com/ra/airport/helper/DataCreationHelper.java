package com.ra.airport.helper;

import com.ra.airport.entity.Flight;
import com.ra.airport.entity.Plane;
import com.ra.airport.entity.Ticket;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class DataCreationHelper {

    private static final String SPACE = " ";

    public static Ticket createTicket() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setTicketNumber("AA111-BB111");
        ticket.setPassengerName("John Dow");
        ticket.setDocument("QQ12345678QQ");
        ticket.setSellingDate(Timestamp.valueOf("2018-07-24 08:00:00"));
        return ticket;
    }

    public static Plane createPlane() {
        Plane plane = new Plane();
        plane.setPlaneId(1);
        plane.setPlateNumber(2);
        plane.setModel(SPACE);
        plane.setType(SPACE);
        plane.setOwner(SPACE);
        return plane;
    }
}
