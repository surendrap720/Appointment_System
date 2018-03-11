package com.example.user.authentication;

/**
 * Created by USER on 3/10/2018.
 */

public class UserDetails {
    private String name;
    private String email;
    private String dob;
    private String mob;
    private String gender;

    public UserDetails(){

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserDetails(String name, String email, String dob, String mob, String gender) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.mob = mob;
        this.gender = gender;
    }
}
