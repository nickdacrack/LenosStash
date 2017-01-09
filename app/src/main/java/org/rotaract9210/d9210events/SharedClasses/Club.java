package org.rotaract9210.d9210events.SharedClasses;

/**
 * Created by Leo on 9/15/2016.
 */
public class Club {
    private String name;
    private String country;
    private String city;
    private String contact;

    public Club(String name, String country, String city, String contact) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
