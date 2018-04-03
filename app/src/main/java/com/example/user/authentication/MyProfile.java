package com.example.user.authentication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView name;
    private TextView dob;
    private TextView mob;
    private TextView gender;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setTitle("My Profile");



        name = (TextView)findViewById(R.id.name);
        dob = (TextView)findViewById(R.id.dob);
        mob = (TextView)findViewById(R.id.mob);
        email = (TextView)findViewById(R.id.email);
        gender= (TextView)findViewById(R.id.gender);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();


        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_name  = dataSnapshot.child("name").getValue().toString();
                String user_dob  = dataSnapshot.child("dob").getValue().toString();
                String user_mob  = dataSnapshot.child("mob").getValue().toString();
                String user_gender = dataSnapshot.child("gender").getValue().toString();
                String user_email = dataSnapshot.child("email").getValue().toString();

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
