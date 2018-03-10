package com.example.user.authentication;

import android.content.Intent;
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
    private EditText Type;
    private Button save;
    private Button skip;
    private FirebaseAuth mAuth;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_hospital_details);

        Fees = (EditText) findViewById(R.id.Fees);
        Location = (EditText) findViewById(R.id.Location);
        Time = (EditText) findViewById(R.id.Time);
        Type = (EditText) findViewById(R.id.Type);
        save = (Button) findViewById(R.id.save);
        skip = (Button) findViewById(R.id.skip);
        mAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        if(intent != null) {
            name = intent.getExtras().getString("name", "");
            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();


        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fees = Fees.getText().toString();
                final String location = Location.getText().toString();
                final String time = Time.getText().toString();
                final String type = Type.getText().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Doctors").child(user_id);
                    DatabaseReference docType = FirebaseDatabase.getInstance().getReference().child(type).child(user_id).child("Name");


                   /* Map newPost = new HashMap();
                    newPost.put("fees", fees);
                    newPost.put("location", location);
                    newPost.put("time", time);
                    newPost.put("type",type);
                    current_user_db.setValue(newPost);*/
                   current_user_db.child("fees").setValue(fees);
                    current_user_db.child("location").setValue(location);
                    current_user_db.child("time").setValue(time);
                    current_user_db.child("type").setValue(type);
                    docType.setValue(name);


                    Toast.makeText(DocHospitalDetails.this, "Your Details are saved.", Toast.LENGTH_SHORT).show();
                    Intent viewAppointment = new Intent(DocHospitalDetails.this,ViewAppointment.class);
                    startActivity(viewAppointment);
                    finish();


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
}