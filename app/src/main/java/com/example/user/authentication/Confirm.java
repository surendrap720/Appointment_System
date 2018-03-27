package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Confirm extends AppCompatActivity {
    Button book;
   DatabaseReference doctor;
   DatabaseReference database;
    private TextView DisplayName;
    private TextView DisplayFees;
    private TextView DisplayLocation;
    private TextView DisplayTime;
    private TextView DisplayContact;
    private TextView DisplayExp;
    private TextView DisplayType;
    private TextView DisplayAvgTime;
    private TextView DisplayEmail;

    private FirebaseAuth mAuth;

   String docId = "";
   int booked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        DisplayName = (TextView)findViewById(R.id.DisplayName);
        DisplayFees = (TextView)findViewById(R.id.DisplayFees);
        DisplayLocation = (TextView)findViewById(R.id.DisplayLocation);
        DisplayTime = (TextView)findViewById(R.id.DisplayTime);
        DisplayContact = (TextView)findViewById(R.id.DisplayContact);
        DisplayExp = (TextView)findViewById(R.id.DisplayExp);
        DisplayType = (TextView)findViewById(R.id.DisplayType);
        DisplayAvgTime = (TextView)findViewById(R.id.DisplayAvgTime);
        DisplayEmail = (TextView)findViewById(R.id.DisplayEmail);
        book = (Button)findViewById(R.id.book);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference();




        Intent intent = getIntent();
        if(intent != null&& intent.getExtras()!=null) {
            docId = intent.getExtras().getString("docId", "");
            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();
        }

       // database = FirebaseDatabase.getInstance().getReference().child("Doctors").child(docId);

       database.child("Doctors").child(docId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String displayName  = dataSnapshot.child("name").getValue().toString();
                String displayFees  = dataSnapshot.child("fees").getValue().toString();
                String displayLocation  = dataSnapshot.child("location").getValue().toString();
                String displayTime = dataSnapshot.child("time").getValue().toString();
                String displayContact  = dataSnapshot.child("mob").getValue().toString();
                String displayExp  = dataSnapshot.child("exp").getValue().toString();
                String displayType  = dataSnapshot.child("type").getValue().toString();
                String displayAvgTime = dataSnapshot.child("avg_time").getValue().toString();
                String displayEmail  = dataSnapshot.child("email").getValue().toString();

                DisplayName.setText(displayName);
                DisplayFees.setText(displayFees);
                DisplayLocation.setText(displayLocation);
                DisplayTime.setText(displayTime);
                DisplayContact.setText(displayContact);
                DisplayExp.setText(displayExp);
                DisplayType.setText(displayType);
                DisplayAvgTime.setText(displayAvgTime);
                DisplayEmail.setText(displayEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(booked==0) {

                    addPatient();
                    booked=1;

                }
                else
                {
                    Toast.makeText(Confirm.this,"You have already booked an appointment",Toast.LENGTH_SHORT).show();
                }

                Intent book = new Intent(Confirm.this,UpComing.class);
                book.putExtra("doctorId",docId);
                startActivity(book);
              //  finish();
            }
        });

    }

    private void addPatient() {

        getUserName();

    }


    private void putPatient(final String patientName, int booked){


         String user_id = mAuth.getCurrentUser().getUid();
     //   final  DatabaseReference Appointment = FirebaseDatabase.getInstance().getReference().child("Appointment");


                Map newPost = new HashMap();
              //  newPost.put("docId",docId);
                newPost.put("patient_name",patientName);
                // newPost.put("app_num",null);

               //database.child("Appointment").child(docId).setValue(newPost);
        database.child("Appointment").child(docId).push().setValue(newPost);





    }



    private void getUserName(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = mAuth.getCurrentUser().getUid();

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
        database.child("User").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final  String patientName  = dataSnapshot.child("name").getValue().toString();
                if (user != null) {

                    booked = 1;
                    putPatient(patientName,booked);

                }
                else{
                    Toast.makeText(Confirm.this,"Please Login",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
