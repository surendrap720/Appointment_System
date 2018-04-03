package com.example.user.authentication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
    private Button book;
    private DatabaseReference database;
    private TextView DisplayName;
    private TextView DisplayFees;
    private TextView DisplayLocation;
    private TextView DisplayTime;
    private TextView DisplayContact;
    private TextView DisplayExp;
    private TextView DisplayEmail;
    private FirebaseAuth mAuth;
    String docId = "";
    String docView = "";
   String displayType = "";
   String uid = "";
   String displayTime = "";
   String displayAvgTime = "";
   String lat = "";
   String lon = "";
   String displayContact = "";
   String displayEmail = "";
   String displayFees = "";
   String displayLocation = "";
   String displayName = "";
   String appointmentNumber="";
   String user_id = "";
    String lattitude,longitude;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
   int dist = 0;
   String hospitalDistance  ="";

   String myAppointmentPushKey = "";
   String time="";
   String startTime = "";
   int intStartTime =0;
   int averageTime =0;
   int timeRemain =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Book Appointment");
        setContentView(R.layout.activity_confirm);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        DisplayName = (TextView)findViewById(R.id.DisplayName);
        DisplayFees = (TextView)findViewById(R.id.DisplayFees);
        DisplayLocation = (TextView)findViewById(R.id.DisplayLocation);
        DisplayTime = (TextView)findViewById(R.id.DisplayTime);
        DisplayContact = (TextView)findViewById(R.id.DisplayContact);
        DisplayExp = (TextView)findViewById(R.id.DisplayExp);
        DisplayEmail = (TextView)findViewById(R.id.DisplayEmail);
        book = (Button)findViewById(R.id.book);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        if(intent != null&& intent.getExtras()!=null) {
            docId = intent.getExtras().getString("docId", "");
            docView = intent.getExtras().getString("docView", "");

        }

       database.child(docView).child(docId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                displayName  = dataSnapshot.child("name").getValue().toString();
                displayFees  = dataSnapshot.child("fees").getValue().toString();
                displayLocation  = dataSnapshot.child("location").getValue().toString();
                displayTime = dataSnapshot.child("time").getValue().toString();
                displayContact  = dataSnapshot.child("mob").getValue().toString();
                String displayExp  = dataSnapshot.child("exp").getValue().toString();
                displayType  = dataSnapshot.child("type").getValue().toString();
                displayAvgTime = dataSnapshot.child("avg_time").getValue().toString();
                lat = dataSnapshot.child("lat").getValue().toString();
                lon = dataSnapshot.child("lon").getValue().toString();
                displayEmail  = dataSnapshot.child("email").getValue().toString();

                DisplayName.setText("Dr. "+displayName);
                DisplayFees.setText("Rs. "+displayFees);
                DisplayLocation.setText(displayLocation);
                DisplayTime.setText(displayTime+" pm");
                DisplayContact.setText(displayContact);
                DisplayExp.setText(displayExp+" years");
                DisplayEmail.setText(displayEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser==null){   // if user doesn't exist he is not allowed to book an appointment
                    Toast.makeText(Confirm.this, "You need to login first", Toast.LENGTH_SHORT).show();
                    Intent authIntent = new Intent(Confirm.this,AuthActivity.class);
                    startActivity(authIntent);
                    finish();
                }
                else {

                       // checkAppointmentExists();
                    addtoMyAppointment();
                    addPatient();
                    senToUpcoming();
                         // when patient books an appointment the details should be stored in MyAppointment node so



                }


            }
        });

    }

    private void senToUpcoming(){
        Intent intent = new Intent(Confirm.this,UpComing.class);
        startActivity(intent);
    }
    private void addPatient() {

        getUserName();

    }

    private void getUserName(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database.child("User").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final  String patientName  = dataSnapshot.child("name").getValue().toString();
                if (user != null) {


                    putPatient(patientName);

                }
                else
                {
                    Toast.makeText(Confirm.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void putPatient(final String patientName){

        Map newPost = new HashMap();
        newPost.put("patient_name",patientName);
        database.child("Appointment").child(docId).push();
        uid = database.child("Appointment").child(docId).push().getKey();
        database.child("MyAppointments").child(user_id).child(myAppointmentPushKey).child("appointmentPushKey").setValue(uid);
        database.child("Appointment").child(docId).child(uid).setValue(newPost);


    }

    private void addtoMyAppointment(){

        Map newPost = new HashMap();
        newPost.put("docId",docId);
        newPost.put("lat",lat);
        newPost.put("lon",lon);
        newPost.put("avg_time",displayAvgTime);
        newPost.put("time",displayTime);
        newPost.put("type",displayType);
        newPost.put("mob",displayContact);
        newPost.put("email",displayEmail);
        newPost.put("location",displayLocation);
        newPost.put("fees",displayFees);
        newPost.put("name",displayName);
        newPost.put("appointmentNumber",appointmentNumber);
        newPost.put("distance",hospitalDistance);
        newPost.put("appointmentPushKey",uid);
        newPost.put("timeRemain",null);
        database.child("MyAppointments").child(user_id).push();
        myAppointmentPushKey = database.child("MyAppointments").child(user_id).push().getKey();
        database.child("MyAppointments").child(user_id).child(myAppointmentPushKey).setValue(newPost);
        getAppointmentNumber();

        setAppointmentDistance();
        getTime();




    }

    private void getTime(){

       startTime =  displayTime.substring(0,1);
       intStartTime = Integer.parseInt(startTime);
       averageTime = Integer.parseInt(displayAvgTime);


        database.child("MyAppointments").child(user_id).child(myAppointmentPushKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                appointmentNumber = dataSnapshot.child("appointmentNumber").getValue().toString();
                timeRemain =2*averageTime;
                time = String.valueOf(timeRemain);
                database.child("MyAppointments").child(user_id).child(myAppointmentPushKey).child("timeRemain").setValue(time);

                Toast.makeText(Confirm.this,"Time "+time,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void getAppointmentNumber(){

        final DatabaseReference database1 = FirebaseDatabase.getInstance().getReference().child("Appointment").child(docId);
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numberOfPatients = (int) dataSnapshot.getChildrenCount();
                appointmentNumber = String.valueOf(numberOfPatients);
               // Toast.makeText(Confirm.this, "Appointment Number is "+appointmentNumber,Toast.LENGTH_SHORT ).show();
                database.child("MyAppointments").child(user_id).child(myAppointmentPushKey).child("appointmentNumber").setValue(appointmentNumber);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setAppointmentDistance(){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Confirm.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Confirm.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Confirm.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                final double latti = location.getLatitude();
                final double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                displayDistance(lattitude,longitude);



            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                displayDistance(lattitude,longitude);



            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                displayDistance(lattitude,longitude);



            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void displayDistance(final String latti, final String longi){

        final double lattitude = Double.valueOf(latti);
        final double longitude = Double.valueOf(longi);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(docView).child(docId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hospital_lat = dataSnapshot.child("lat").getValue().toString();
                double hospi_lat = Double.valueOf(hospital_lat);
                String hospital_lon = dataSnapshot.child("lon").getValue().toString();
                double hospi_lon = Double.valueOf(hospital_lon);
                double distance =  calculateDistance(lattitude,longitude,hospi_lat,hospi_lon);
                dist = (int)distance;
                hospitalDistance = String.valueOf(dist);

               database.child("MyAppointments").child(user_id).child(myAppointmentPushKey).child("distance").setValue(hospitalDistance);
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
