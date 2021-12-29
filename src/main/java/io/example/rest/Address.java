package io.example.rest;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String number;

    public String getFullAddress() {
        return number + street + " St. " + city + ", " + state;
    }
}
