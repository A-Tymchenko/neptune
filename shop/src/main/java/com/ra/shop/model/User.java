package com.ra.shop.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity represents an user that contains all important information.
 */
public class User implements Serializable {

    private long id;
    private String phoneNumber;
    private String name;
    private String secondName;
    private String country;
    private String emailAddress;

    public User() {
        super();
    }

    public User(Long id, String phoneNumber, String name, String secondName,
                 String country, String emailAddress) {
        super();
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.secondName = secondName;
        this.country = country;
        this.emailAddress = emailAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{"
                + "id="
                + id
                + ", phoneNumber="
                + phoneNumber
                + ", name="
                + name
                + ", secondName="
                + secondName
                + ", country="
                + country
                + ", emailAddress="
                + emailAddress
                + '}';
    }
}
