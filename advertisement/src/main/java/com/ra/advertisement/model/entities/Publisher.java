package com.ra.advertisement.model.entities;

public class Publisher {

    private Long pubId;
    private String name;
    private String address;
    private String telephone;
    private String country;

    public Publisher() {

    }

    public Publisher(final Long pubId, final String name, final String address, final String telephone,
                     final String country) {
        this.pubId = pubId;
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
