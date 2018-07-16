package com.ra.advertisement.entity;

public class Provider {
    private Long provId;
    private String name;
    private String address;
    private String telephone;
    private String country;

    public Provider() {

    }

    public Provider(final Long provId, final String name, final String address,
                    final String telephone, final String country) {
        this.provId = provId;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.country = country;
    }

    public Provider(final String name, final String address,
                    final String telephone, final String country) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Long getProvId() {
        return provId;
    }

    public void setProvId(final Long provId) {
        this.provId = provId;
    }

    @Override
    public String toString() {
        return "Provider{"
                + "provId=" + provId
                + ", name='" + name + '\''
                + ", address='" + address + '\''
                + ", telephone='" + telephone + '\''
                + ", country='" + country + '\''
                + '}';
    }
}
