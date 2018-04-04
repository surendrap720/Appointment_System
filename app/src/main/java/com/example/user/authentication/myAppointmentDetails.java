package com.example.user.authentication;

/**
 * Created by USER on 4/1/2018.
 */

public class myAppointmentDetails {
   private int appointmentNumber;
    private int avg_time;
    private String distance;
    private String docId;
    private String email;
    private String fees;
    private String location;
    private String mob;
    private String name;
    private String timing ;
    private String type;

    public myAppointmentDetails(int appointmentNumber, int avg_time, String distance, String docId, String email, String fees, String location, String mob, String name, String timing, String type) {
       this.appointmentNumber = appointmentNumber;
        this.avg_time = avg_time;
        this.distance = distance;
        this.docId = docId;
        this.email = email;
        this.fees = fees;
        this.location = location;
        this.mob = mob;
        this.name = name;
        this.timing = timing;
        this.type = type;
    }

    public int getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(int appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public int getAvg_time() {
        return avg_time;
    }

    public void setAvg_time(int avg_time) {
        this.avg_time = avg_time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public myAppointmentDetails() {
    }
}
