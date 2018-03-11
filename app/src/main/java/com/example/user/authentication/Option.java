package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Option extends AppCompatActivity {


    private Button doctor;
    private Button patient;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        doctor = (Button) findViewById(R.id.doctor);
        patient = (Button) findViewById(R.id.patient);
        mAuth = FirebaseAuth.getInstance();

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkDoctorExists();
                Intent doctor = new Intent(Option.this, DoctorRegistration.class);
                startActivity(doctor);
                finish();
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent patient = new Intent(Option.this, MainActivity.class);
                startActivity(patient);
                finish();
            }
        });

    }

    private void checkDoctorExists(){ //to check if the user already registered and has his data in the database

        final String user_id = mAuth.getCurrentUser().getUid(); //get the id of the currently logged in user
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(user_id)){ //if there is a child in the database having this particular user_id
                    //send  him directly to his home page rather than register

                    Intent registerintent = new Intent(Option.this, DoctorRegistration.class);
                    startActivity(registerintent);
                    finish();

                }
                else{   //if the user is only logged in and not registered than the we need to get him registered from MainActivity
                    // Toast.makeText(AuthActivity.this,"You need set your account",Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(Option.this, ViewAppointment.class);
                    startActivity(HomeIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
