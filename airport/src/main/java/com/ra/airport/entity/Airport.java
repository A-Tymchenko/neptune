package com.ra.airport.entity;

import java.util.Objects;

public class Airport {

    private Integer apId;
    private String apName;
    private int apNum;
    private String apType;
    private String address;
    private int terminalCount;

    public Airport(final Integer apId, final String apName, final int apNum,
                   final String apType, final String address, final int terminalCount) {
        this.apId = apId;
        this.apName = apName;
        this.apNum = apNum;
        this.apType = apType;
        this.address = address;
        this.terminalCount = terminalCount;
    }

    public Airport(){

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

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Airport airport = (Airport) object;
        return apNum == airport.apNum
                && terminalCount == airport.terminalCount
                && Objects.equals(apId, airport.apId)
                && Objects.equals(apName, airport.apName)
                && Objects.equals(apType, airport.apType)
                && Objects.equals(address, airport.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(apId, apName, apNum, apType, address, terminalCount);
    }

    @Override
    public String toString() {
        return "Airport{"
                + "apId=" + apId
                + ", apName='" + apName + '\''
                + ", apNum=" + apNum
                + ", apType='" + apType + '\''
                + ", address='" + address + '\''
                + ", terminalCount=" + terminalCount
                + '}';
    }
}
