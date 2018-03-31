package com.example.user.authentication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private EditText Avg_Time;
    private EditText ClinicName;
    private EditText Experience;
    private Button save;
    private Button skip;
    private FirebaseAuth mAuth;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_hospital_details);

        Fees = (EditText) findViewById(R.id.Fees);
        Location = (EditText) findViewById(R.id.Location);
        Time = (EditText) findViewById(R.id.Time);

        Avg_Time = (EditText) findViewById(R.id.Avg_Time);
        ClinicName = (EditText) findViewById(R.id.ClinicName);
        Experience = (EditText)findViewById(R.id.Experience);
        save = (Button) findViewById(R.id.save);
        skip = (Button) findViewById(R.id.skip);
        mAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        if(intent != null) {
            type = intent.getExtras().getString("type", "");
            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();


        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fees = Fees.getText().toString();
                final String location = Location.getText().toString();
                final String time = Time.getText().toString();

                final String avg_time = Avg_Time.getText().toString();
                final String clinic_name = ClinicName.getText().toString();
                final String exp = Experience.getText().toString();

                getLatLong(location);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child(type).child(user_id);
                   // DatabaseReference docType = FirebaseDatabase.getInstance().getReference().child(type).child(user_id).child("Name");
                    //separate list of doctors

                   current_user_db.child("fees").setValue(fees);
                    current_user_db.child("location").setValue(location);
                    current_user_db.child("time").setValue(time);
                    current_user_db.child("avg_time").setValue(avg_time);
                    current_user_db.child("clinic_name").setValue(clinic_name);
                    current_user_db.child("exp").setValue(exp);
                 //  docType.setValue(name);


                    Toast.makeText(DocHospitalDetails.this, "Your Details are saved.", Toast.LENGTH_SHORT).show();
                    Intent viewAppointment = new Intent(DocHospitalDetails.this,ViewAppointment.class);
                    startActivity(viewAppointment);



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
            String locationAddress;
            String user_id = mAuth.getCurrentUser().getUid();
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    String address = locationAddress;
                    String lat = address.substring(0,10);
                    String lon = address.substring(11);

                   // Toast.makeText(DocHospitalDetails.this,"address is :"+lon,Toast.LENGTH_SHORT).show();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child(type).child(user_id);
                    current_user_db.child("lat").setValue(lat);
                    current_user_db.child("lon").setValue(lon);

                    break;
                default:
                    locationAddress = null;
            }

        }
    }
}