package com.example.user.authentication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DocHospitalDetails extends AppCompatActivity {

    private EditText Fees;
    private EditText Time;
    private EditText Location;
    private EditText maxPatients;
    private EditText Avg_Time;
    private EditText ClinicName;
    private EditText Experience;
    private Button save;
    private Button skip;
    private FirebaseAuth mAuth;
    private String type = "";
    private String fees = "";
    private String location = "";
    private String avg_time = "";
    private String time = "";
    private String clinic_name = "";
    private String exp = "";
    private String maxPatient = "";
    private String user_id = "";
    private String lat = "";
    private String lon = "";
    private String address = "";
    private String locationAddress = "";
    int maximumPatient = 0;
    int averageTime = 0;
    private  DatabaseReference reference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Hospital Details");
        setContentView(R.layout.activity_doc_hospital_details);

        Fees = (EditText) findViewById(R.id.Fees);
        Location = (EditText) findViewById(R.id.Location);
        Time = (EditText) findViewById(R.id.Time);
        maxPatients = (EditText)findViewById(R.id.maxPatients);
        Avg_Time = (EditText) findViewById(R.id.Avg_Time);
        ClinicName = (EditText) findViewById(R.id.ClinicName);
        Experience = (EditText)findViewById(R.id.Experience);
        save = (Button) findViewById(R.id.save);
        skip = (Button) findViewById(R.id.skip);
        mAuth = FirebaseAuth.getInstance();
        reference  = FirebaseDatabase.getInstance().getReference();


        Intent intent = getIntent();
        if(intent != null) {
            type = intent.getExtras().getString("type", "");
            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fees = Fees.getText().toString();
                location = Location.getText().toString();
                time = Time.getText().toString();
                avg_time = Avg_Time.getText().toString();
                averageTime = Integer.parseInt(avg_time);
                clinic_name = ClinicName.getText().toString();
                exp = Experience.getText().toString();
                maxPatient = maxPatients.getText().toString();
                maximumPatient = Integer.parseInt(maxPatient);

                getLatLong(location);

                if(TextUtils.isEmpty(fees)||TextUtils.isEmpty(location)||TextUtils.isEmpty(time)||TextUtils.isEmpty(avg_time)||TextUtils.isEmpty(clinic_name)||TextUtils.isEmpty(exp)||TextUtils.isEmpty(maxPatient)){
                    Toast.makeText(DocHospitalDetails.this,"Fields are empty",Toast.LENGTH_LONG).show();
                }
                else if(avg_time.length()<=0||avg_time.length()>2||maxPatient.length()<=0||maxPatient.length()>2){

                    Toast.makeText(DocHospitalDetails.this,"Please Enter proper average time and maximum patient details",Toast.LENGTH_LONG).show();
                }
                else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user_id = mAuth.getCurrentUser().getUid();
                        reference.child(type).child(user_id).child("fees").setValue(fees);
                        reference.child(type).child(user_id).child("location").setValue(location);
                        reference.child(type).child(user_id).child("time").setValue(time);
                        reference.child(type).child(user_id).child("avg_time").setValue(averageTime);
                        reference.child(type).child(user_id).child("clinic_name").setValue(clinic_name);
                        reference.child(type).child(user_id).child("exp").setValue(exp);
                        reference.child(type).child(user_id).child("maxPatient").setValue(maximumPatient);

                        Map newPost = new HashMap();
                        newPost.put("currentPatient", 0);
                        newPost.put("maxPatient", maximumPatient);
                        reference.child("PatientCount").child(user_id).setValue(newPost);

                        Toast.makeText(DocHospitalDetails.this, "Your Details are saved.", Toast.LENGTH_SHORT).show();
                        Intent viewAppointment = new Intent(DocHospitalDetails.this, ViewAppointment.class);
                        startActivity(viewAppointment);


                    }
                }
            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAppointment = new Intent(DocHospitalDetails.this,ViewAppointment.class);
                startActivity(viewAppointment);
                finish();

            }
        });


    }

    private void getLatLong(String address){

        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address,
                getApplicationContext(), new GeocoderHandler());

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            user_id = mAuth.getCurrentUser().getUid();
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    address = locationAddress;
                    Toast.makeText(DocHospitalDetails.this, "address is " + locationAddress , Toast.LENGTH_SHORT).show();

                    lat = address.substring(0,10);
                    lon = address.substring(11);
                    reference.child(type).child(user_id).child("lat").setValue(lat);
                    reference.child(type).child(user_id).child("lon").setValue(lon);

                    break;
                default:
                    locationAddress = null;
            }

        }
    }
}