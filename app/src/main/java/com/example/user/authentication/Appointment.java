package com.example.user.authentication;

/**
 * Created by USER on 3/10/2018.
 */

public class Appointment {

    private String name;

  /*  public Appointment(String name) {
        this.name = name;
    }*/

  @Override
  public String toString(){
      return this.name;
  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Appointment(){

    }
}
