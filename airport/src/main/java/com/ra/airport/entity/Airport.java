package com.ra.airport.entity;

import java.util.Objects;

public class Airport {

    private Integer apid;
    private String apname;
    private int apnum;
    private String aptype;
    private String addresses;
    private int terminalcount;

    public Airport(final Integer apid, final String apname, final int apnum, final String aptype, final String addresses,
                   final int terminalcount) {
        this.apid = apid;
        this.apname = apname;
        this.apnum = apnum;
        this.aptype = aptype;
        this.addresses = addresses;
        this.terminalcount = terminalcount;
    }

    public Integer getApid() {
        return apid;
    }

    public String getApname() {
        return apname;
    }

    public int getApnum() {
        return apnum;
    }

    public String getAptype() {
        return aptype;
    }

    public String getAddresses() {
        return addresses;
    }

    public int getTerminalcount() {
        return terminalcount;
    }

    public void setApid(final Integer apid) {
        this.apid = apid;
    }

    public void setApname(final String apname) {
        this.apname = apname;
    }

    public void setApnum(final int apnum) {
        this.apnum = apnum;
    }

    public void setAptype(final String aptype) {
        this.aptype = aptype;
    }

    public void setAddresses(final String addresses) {
        this.addresses = addresses;
    }

    public void setTerminalcount(final int terminalcount) {
        this.terminalcount = terminalcount;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Airport airport = (Airport) object;
        return apnum == airport.apnum
                && terminalcount == airport.terminalcount
                && Objects.equals(apid, airport.apid)
                && Objects.equals(apname, airport.apname)
                && Objects.equals(aptype, airport.aptype)
                && Objects.equals(addresses, airport.addresses);
    }

    @Override
    public int hashCode() {

        return Objects.hash(apid, apname, apnum, aptype, addresses, terminalcount);
    }
}
