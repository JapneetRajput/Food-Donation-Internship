package com.example.internship;

public class dataExtract {

    dataExtract(){};

    private String name,number,email;

    public dataExtract(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }
}
