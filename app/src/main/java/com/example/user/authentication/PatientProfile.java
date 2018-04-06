package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView name;
    private TextView dob;
    private TextView mob;
    private TextView gender;
    private TextView email;
    String patientId = "";
    String user_name = "";
    String user_dob = "";
    String user_mob = "";
    String user_email = "";
    String user_gender = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            patientId = intent.getExtras().getString("patientId", "");

        }

        name = (TextView)findViewById(R.id.name);
        dob = (TextView)findViewById(R.id.dob);
        mob = (TextView)findViewById(R.id.mob);
        email = (TextView)findViewById(R.id.email);
        gender= (TextView)findViewById(R.id.gender);
        mAuth = FirebaseAuth.getInstance();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("User").child(patientId);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user_name  = dataSnapshot.child("name").getValue().toString();
                user_dob  = dataSnapshot.child("dob").getValue().toString();
                user_mob  = dataSnapshot.child("mob").getValue().toString();
                user_gender = dataSnapshot.child("gender").getValue().toString();
                user_email = dataSnapshot.child("email").getValue().toString();

                name.setText(user_name);
                dob.setText(user_dob);
                mob.setText(user_mob);
                gender.setText(user_gender);
                email.setText(user_email);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
