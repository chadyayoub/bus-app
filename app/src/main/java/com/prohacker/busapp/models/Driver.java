package com.prohacker.busapp.models;

public class Driver {
    //ints
    private int startDate;
    private int busId;

    //floats
    private float currentLat;
    private float currentLong;

    //booleans
    private boolean isActiveToday;
    private boolean isOnWay;

    //Strings
    private String estimatedRouteTime;
    private String departureTime;

    //getters
    public int getStartDate() {
        return startDate;
    }

    public int getBusId() {
        return busId;
    }

    public float getCurrentLat() {
        return currentLat;
    }

    public float getCurrentLong() {
        return currentLong;
    }

    public boolean isActiveToday() {
        return isActiveToday;
    }

    public boolean isOnWay() {
        return isOnWay;
    }

    public String getEstimatedRouteTime() {
        return estimatedRouteTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    //setters
    public void setCurrentLat(float currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLong(float currentLong) {
        this.currentLong = currentLong;
    }
}
