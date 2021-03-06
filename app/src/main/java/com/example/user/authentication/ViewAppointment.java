package com.example.user.authentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewAppointment extends AppCompatActivity   {


    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private Button start;
    private Button stop;
    private Button send_button;
    private EditText messageEditText;
    private String startFormat="";
    private String stopFormat="";
    private String startHr="";
    private String startMm="";
    private String stopHr="";
    private String stopMm="";
    private String user_id="";
    private String patientId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Appointment List");
        setContentView(R.layout.activity_view_appointment);
        start = findViewById(R.id.start_button);
        stop = findViewById(R.id.stop_button);



        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PatientList").child(user_id);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm");
                startFormat = simpleDateFormat.format(new Date());
                startHr = startFormat.substring(0,2);
                startMm = startFormat.substring(3);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm");
                stopFormat = simpleDateFormat.format(new Date());
                stopHr=startFormat.substring(0,2);
                stopMm = startFormat.substring(3);
                calDuration();
                mAuth.signOut();
                sendToAuth();
                Toast.makeText(ViewAppointment.this,"You have logged out",Toast.LENGTH_SHORT).show();

            }
        });


        FirebaseRecyclerAdapter<PatientList, ViewAppointment.DocViewHolder> adapter = new FirebaseRecyclerAdapter<PatientList, ViewAppointment.DocViewHolder>(
                PatientList.class,
                R.layout.individual_row,
                ViewAppointment.DocViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(final ViewAppointment.DocViewHolder viewHolder, final PatientList model, int position) {


                viewHolder.setAppointmentNumber(model.getAppointmentNumber());
                viewHolder.setPatient_name(model.getPatient_name());
                patientId = model.getPatientId();


               viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent patientProfile = new Intent(ViewAppointment.this,PatientProfile.class);
                        patientProfile.putExtra("patientId",patientId);
                        startActivity(patientProfile);

                    }
                });

            }

        };

        recyclerView.setAdapter(adapter);

    }


    private void calDuration(){
       int starthour  = Integer.parseInt(startHr);
       int startmin = Integer.parseInt(startMm);
        int stophour  = Integer.parseInt(stopHr);
        int stopmin  = Integer.parseInt(stopMm);
       int duration_hour = stophour - starthour;
       int duration_minute = stopmin-startmin;
      int totalTime = duration_hour*60+duration_minute;

    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        TextView AppointmentNumber;
        private CardView card;


        public DocViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            Name = (TextView) itemView.findViewById(R.id.Name);
            AppointmentNumber = (TextView) itemView.findViewById(R.id.AppointmentNumber);

        }

        public void setPatient_name(String patient_name) {
            Name.setText(patient_name);
        }

        public void setAppointmentNumber(int appointmentNumber) {

            AppointmentNumber.setText(Integer.toString(appointmentNumber));
        }
    }



    private void sendToAuth() {
        Intent authIntent = new Intent(ViewAppointment.this, AuthActivity.class);
        authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(authIntent);
        finish();
    }





}
