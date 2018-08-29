package com.ra.airport.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AirportDTO {

    private Integer apId;
    @NotNull
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX_LETERS, message = "Name should have atleast 2 characters")
    private String apName;
    @NotNull
    @Min(LengthHelper.MIN)
    @Max(LengthHelper.MAX_NUMBER)
    private int apNum;
    @NotNull
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX_LETERS, message = "Name should have atleast 2 characters")
    private String apType;
    @NotNull
    @Size(min = LengthHelper.MIN, max = LengthHelper.MAX_LETERS, message = "Name should have atleast 2 characters")
    private String address;
    @NotNull
    @Min(LengthHelper.MIN)
    @Max(LengthHelper.MAX_TERMINALS)
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
