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
    String appointmentPushKey = "";
    String docId = "";
    String remainingTime = "";
    String startTime = "";


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

        reference = FirebaseDatabase.getInstance().getReference().child("MyAppointments").child(user_id);
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
                viewHolder.setType(model.getType());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setFees(model.getFees());
              //  viewHolder.setTime(model.getTime());
                startTime =  model.getTime().substring(0,1);

                viewHolder.setDistance(model.getDistance());
                viewHolder.setTimeRemain(model.getTimeRemain());
                appointmentPushKey = model.getAppointmentPushKey();
                docId = model.getDocId();


               viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference cancelAppointment = FirebaseDatabase.getInstance().getReference().child("Appointment").child(docId).child(appointmentPushKey);
                        reference.removeValue();
                        cancelAppointment.removeValue();
                        Toast.makeText(UpComing.this,"Appointment has been cancelled",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpComing.this,Home.class);
                        startActivity(intent);

                    }
                });

            }
        };


        recyclerView.setAdapter(adapter);




    }
    public static class DocViewHolder extends RecyclerView.ViewHolder {
        TextView AppointmentNumber;
        TextView Name;
        TextView Distance;
        TextView Location;
        TextView Fees;
        TextView Time;
        TextView Type;
      //  TextView timeRemaining;
        private ImageButton save;
        private Button cancel;

        //nq  TextView Id;
        // TextView Location;
        private CardView card;

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
        //    timeRemaining = (TextView) itemView.findViewById(R.id.timeRemaining);
            save = (ImageButton) itemView.findViewById(R.id.save);
            cancel = (Button) itemView.findViewById(R.id.cancel);

        }

        public void setName(String name) {

            Name.setText("Dr. " + name);
        }

        public void setFees(String fees) {

            Fees.setText("Rs. " + fees);
        }


       /* public void setTime(String time) {

            Time.setText("Time: " + time);
        }*/

        public void setType(String type) {
            Type.setText(type);
        }


        public void setLocation(String location) {

            Location.setText(location);
        }


        public void setAppointmentNumber(String appointmentNumber) {
            AppointmentNumber.setText(appointmentNumber);
        }

        public void setDistance(String distance) {
            Distance.setText(distance);
        }

        public void setTimeRemain(String timeRemain) {

            Time.setText("2hr + "+timeRemain);
        }
    }




       /* cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   String id = uid;
                database.child("Appointment").child(doctorId).child(uid).removeValue();
                senToDoctors();
                Toast.makeText(UpComing.this,"Your appointment has been cancelled",Toast.LENGTH_SHORT).show();

            }
        });*/

    }



