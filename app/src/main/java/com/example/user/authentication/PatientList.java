package com.example.user.authentication;

/**
 * Created by USER on 3/30/2018.
 */

public class PatientList {

private String patient_name;
private String patientId ;
private int appointmentNumber;

    public PatientList(String patient_name,String patientId, int appointmentNumber) {

        this.patient_name = patient_name;
        this.patientId = patientId;
        this.appointmentNumber = appointmentNumber;
    }

    public int getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(int appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

