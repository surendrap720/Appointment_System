package com.example.user.authentication;

/**
 * Created by USER on 2/25/2018.
 */

public class DocDetail {

    private String name;
    private String email;
    private String dob;
    private String mob;
    private String time;
    private String location;
    private String gender;
    private String fees;
    private String id;
    private int avg_time;
    private String type;
    private String clinic_name;
    private String experience;

    public DocDetail(String name, String email, String dob, String mob, String time, String location, String gender, String fees, String id, int avg_time, String type, String clinic_name, String experience) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.mob = mob;
        this.time = time;
        this.location = location;
        this.gender = gender;
        this.fees = fees;
        this.id = id;
        this.avg_time = avg_time;
        this.type = type;
        this.clinic_name = clinic_name;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvg_time() {
        return avg_time;
    }

    public void setAvg_time(int avg_time) {
        this.avg_time = avg_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public DocDetail() {
    }






}
