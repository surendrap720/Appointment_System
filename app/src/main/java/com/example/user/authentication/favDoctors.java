package com.example.user.authentication;

/**
 * Created by USER on 4/1/2018.
 */

public class favDoctors {

    private String name;
    private String type;
    private String clinicName;
    private String location;
    private String fees;
    private String time;
    private String id;



    public favDoctors(String name, String type, String clinicName, String location, String fees, String time, String id) {
        this.name = name;
        this.type = type;
        this.clinicName = clinicName;
        this.location = location;
        this.fees = fees;
        this.time = time;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public favDoctors() {
    }
}
