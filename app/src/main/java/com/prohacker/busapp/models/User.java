package com.prohacker.busapp.models;

public class User {
    //ints
    private int id; public int getId(){return id;}
    private int driverId; public int getDriverId(){return driverId;}
    private int rideId; public int getRideId(){return rideId;}

    //strings
    private String firstName; public String getFirstName(){return firstName;} public void setFirstName(String input){firstName=input;}
    private String lastName; public String getLastName(){return lastName;} public void setLastName(String input){lastName=input;}

    //booleans
    private boolean isDriver; public boolean getIsDriver(){return isDriver;}
    private boolean isAdmin; public boolean getIsAdmin(){return isAdmin;}
    private boolean isRequestingRide; public boolean getIsRequestingRide(){return isRequestingRide;}
}
