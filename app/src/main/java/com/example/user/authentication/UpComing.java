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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class UpComing extends AppCompatActivity {

    private Button cancel_appointment;
    private FirebaseAuth mAuth;
    private TextView Appoint_Number;
    private TextView Appoint_Day;
    private TextView Appoint_Time;
    private TextView Appoint_TimeRemain;
    private TextView Appoint_DocStatus;
    private TextView Appoint_Distance;
    LocationManager locationManager;
    String lattitude,longitude;
    private static final int REQUEST_LOCATION = 1;
    String uid="";
    String doctorId ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        cancel_appointment = (Button)findViewById(R.id.cancel_appointment);
        mAuth = FirebaseAuth.getInstance();
        Appoint_Number = (TextView)findViewById(R.id.Appoint_Number);
        Appoint_Day = (TextView)findViewById(R.id.Appoint_Day);
        Appoint_Time = (TextView)findViewById(R.id.Appoint_Time);
        Appoint_TimeRemain = (TextView)findViewById(R.id.Appoint_TimeRemain);
        Appoint_DocStatus = (TextView)findViewById(R.id.Appoint_DocStatus);
        Appoint_Distance = (TextView)findViewById(R.id.Appoint_Distance);

        Intent intent = getIntent();
        if(intent != null && intent.getExtras()!=null) {
           doctorId   = intent.getExtras().getString("doctorId", "");
           uid = intent.getExtras().getString("pushId","");
       //   Toast.makeText(UpComing.this,"pushid is"+uid,Toast.LENGTH_SHORT).show();
            setAppointmentNumber(doctorId);
            setAppointmentTime(doctorId);
            setAppointmentDistance(doctorId);
          // setAppointmentTimeRemain();

        }

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = uid;
                database.child("Appointment").child(doctorId).child(id).removeValue();
                senToDoctors();
                Toast.makeText(UpComing.this,"Your appointment has been cancelled",Toast.LENGTH_SHORT).show();

            }
        });

    }

   /* private void setAppointmentTimeRemain(){

       DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Doctors").child(doctorId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String avg_time = dataSnapshot.child("avg_time").getValue().toString();
                int average_time = Integer.parseInt(avg_time);
               CharSequence num =  Appoint_Number.getText();
                int number = Integer.parseInt(num.toString());
                int minutes = average_time*number;
                Appoint_TimeRemain.setText("You should arrive "+ minutes+" after the doctor's timing");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }*/

    private void senToDoctors(){
        Intent doctors = new Intent(UpComing.this,Doctor.class);
        startActivity(doctors);
        finish();
    }
    private void setAppointmentNumber(String doctorId){

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Appointment").child(doctorId);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numberOfPatients = (int) dataSnapshot.getChildrenCount();
                String totalPatients = String.valueOf(numberOfPatients);
                Appoint_Number.setText(totalPatients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void setAppointmentTime(String doctorId){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors").child(doctorId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String time = dataSnapshot.child("time").getValue().toString();
              //  String docMessage = dataSnapshot.child("message").getValue().toString();
                String avg_time = dataSnapshot.child("avg_time").getValue().toString();
                getReachingTime(avg_time);//integer value cannot be set to textView . It should be String
                Appoint_Time.setText(time);
               // Appoint_DocStatus.setText(docMessage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getReachingTime(String avg_time){

    }


    private void setAppointmentDistance(String docId){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation(docId);
        }
    }

    private void getLocation(String doctorId) {
        if (ActivityCompat.checkSelfPermission(UpComing.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (UpComing.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(UpComing.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                final double latti = location.getLatitude();
                final double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                displayDistance(lattitude,longitude,doctorId);



            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                displayDistance(lattitude,longitude,doctorId);



            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                displayDistance(lattitude,longitude,doctorId);



            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void displayDistance(final String latti, final String longi,String doctorId){

        final double lattitude = Double.valueOf(latti);
        final double longitude = Double.valueOf(longi);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Doctors").child(doctorId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hospital_lat = dataSnapshot.child("lat").getValue().toString();
                double hospi_lat = Double.valueOf(hospital_lat);
                String hospital_lon = dataSnapshot.child("lon").getValue().toString();
                double hospi_lon = Double.valueOf(hospital_lon);
                double distance =  calculateDistance(lattitude,longitude,hospi_lat,hospi_lon);
                int dist = (int)distance;
                Appoint_Distance.setText(dist+" km");
                // Toast.makeText(UpComing.this,"distance is: "+dist,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
