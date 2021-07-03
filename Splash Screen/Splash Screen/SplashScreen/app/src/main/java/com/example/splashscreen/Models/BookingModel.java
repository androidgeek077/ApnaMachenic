package com.example.splashscreen.Models;

public class BookingModel {
    String name, type, datetime;

    public String getName() {
        return name;
    }

    public BookingModel() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public BookingModel(String name, String type, String datetime) {
        this.name = name;
        this.type = type;
        this.datetime = datetime;
    }
}
