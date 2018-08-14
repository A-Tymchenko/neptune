package com.ra.advertisement.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PublisherDto {
    private Long pubId;

    @NotEmpty(message = "Name can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Name should be longer than 4 and less than 50")
    private String name;

    @NotEmpty(message = "Address can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Address should be longer than 4 and less than 50")
    private String address;

    @NotEmpty(message = "Telephone can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Telephone should be longer than 4 and less than 50")
    @Pattern(regexp = "[0-9]+", message = "Telephone should be nummbers only")
    private String telephone;

    @NotEmpty(message = "Country can't be empty")
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "Country should be longer than 4 and less than 50")
    private String country;

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 50;

    public PublisherDto() {
    }

    public PublisherDto(final Long pubId, final String name, final String address, final String telephone,
                        final String country) {
        this.pubId = pubId;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.country = country;
    }

    public PublisherDto(final String name, final String address, final String telephone,
                        final String country) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public Long getPubId() {
        return pubId;
    }

    public void setPubId(final Long pubId) {
        this.pubId = pubId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Publisher{"
                + "pubId=" + pubId
                + ", name='" + name + '\''
                + ", address='" + address + '\''
                + ", telephone='" + telephone + '\''
                + ", country='" + country + '\''
                + '}';
    }
}
