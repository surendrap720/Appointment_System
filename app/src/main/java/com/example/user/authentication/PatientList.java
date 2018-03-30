package com.example.user.authentication;

/**
 * Created by USER on 3/30/2018.
 */

public class PatientList {

private String patient_name;

    public PatientList(String patient_name) {

        this.patient_name = patient_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public PatientList(){

    }
}

