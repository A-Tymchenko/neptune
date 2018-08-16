package com.ra.airport.dto;

public class AirportDTO {

    private Integer apId;
    private String apName;
    private int apNum;
    private String apType;
    private String address;
    private int terminalCount;

    public AirportDTO() {

    }

    public Integer getApId() {
        return apId;
    }

    public void setApId(final Integer apId) {
        this.apId = apId;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(final String apName) {
        this.apName = apName;
    }

    public int getApNum() {
        return apNum;
    }

    public void setApNum(final int apNum) {
        this.apNum = apNum;
    }

    public String getApType() {
        return apType;
    }

    public void setApType(final String apType) {
        this.apType = apType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public int getTerminalCount() {
        return terminalCount;
    }

    public void setTerminalCount(final int terminalCount) {
        this.terminalCount = terminalCount;
    }
}
