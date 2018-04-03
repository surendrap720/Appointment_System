package com.example.user.authentication;

import android.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.*;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class UpComing extends AppCompatActivity {

    private RecyclerView recyclerView;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    String user_id = "";
    String count = "";
    int counter = 0;

    @Override
    protected void onStart() {

        super.onStart();

        reference = FirebaseDatabase.getInstance().getReference().child("Appointments").child(user_id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              counter = (int) dataSnapshot.getChildrenCount();
              if(counter==0){

                  callHome();
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void callHome(){
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle(" No Appointment Booked")
                .setMessage("Would you like to book an Appointment ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intentHome = new Intent(UpComing.this,Home.class);
                        startActivity(intentHome);
                        finish();
                    }
                }).create().show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Appointment Details");
        setContentView(R.layout.activity_up_coming);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Appointments").child(user_id);

        checkAppointmentBooked();
        FirebaseRecyclerAdapter<myAppointmentDetails, UpComing.DocViewHolder> adapter = new FirebaseRecyclerAdapter<myAppointmentDetails, UpComing.DocViewHolder>(
                myAppointmentDetails.class,
                R.layout.myappointments,
                UpComing.DocViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(final UpComing.DocViewHolder viewHolder, final myAppointmentDetails model, int position) {
                viewHolder.setAppointmentNumber(model.getAppointmentNumber());
                viewHolder.setName(model.getName());
                viewHolder.setDistance(model.getDistance());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setFees(model.getFees());
                viewHolder.setTime(model.getTiming());
                viewHolder.setType(model.getType());



            }
        };


        recyclerView.setAdapter(adapter);


    }

    private void checkAppointmentBooked(){


    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {
        TextView AppointmentNumber;
        TextView Name;
        TextView Distance;
        TextView Location;
        TextView Fees;
        TextView Time;
        TextView Type;
        CardView card;

        public DocViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            AppointmentNumber = (TextView) itemView.findViewById(R.id.AppointmentNumber);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Distance = (TextView) itemView.findViewById(R.id.Distance);
            Location = (TextView) itemView.findViewById(R.id.Location);
            Fees = (TextView) itemView.findViewById(R.id.Fees);
            Time = (TextView) itemView.findViewById(R.id.Time);
            Type = (TextView) itemView.findViewById(R.id.Type);


        }




        public void setName(String name) {

            Name.setText("Dr. "+name);
        }

        public void setAppointmentNumber(int appointmentNumber) {

            AppointmentNumber.setText("No: "+appointmentNumber);
        }

        public void setDistance(String distance) {

            Distance.setText(distance+" km");
        }

        public void setLocation(String location) {

            Location.setText(location);
        }

        public void setFees(String fees) {
            Fees.setText("Rs "+fees);
        }

        public void setTime(String timing) {
            Time.setText("Time:"+timing);
        }

        public void setType(String type) {

            Type.setText(type);
        }
    }
    @Override
    public void onBackPressed() {

        Intent doctor = new Intent(UpComing.this,Home.class);
        startActivity(doctor);
    }


}