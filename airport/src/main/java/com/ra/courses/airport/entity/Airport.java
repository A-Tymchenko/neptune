package com.ra.courses.airport.entity;

public class Airport {

    private String apid;
    private String apname;
    private int apnum;
    private String aptype;
    private String addresses;
    private int terminalcount;

    public Airport(final String apid, final String apname, final int apnum, final String aptype, final String addresses,
                   final int terminalcount) {
        this.apid = apid;
        this.apname = apname;
        this.apnum = apnum;
        this.aptype = aptype;
        this.addresses = addresses;
        this.terminalcount = terminalcount;
    }

    public String getApid() {
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

    public void setApid(final String apid) {
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
}
