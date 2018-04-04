package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DoctorRegistration extends AppCompatActivity {



    private EditText Name;
    private EditText Email;
    private EditText Dob;
    private EditText Mob;
    private EditText Type;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button Register_button;
    private Button logout_btn;
    private FirebaseAuth mAuth;
    DatabaseReference reference;

     String name = "";
     String email ="";
     String dob = "";
     String mob = "";
     String type = "";
     String gender = "";
     String user_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Registration");
        setContentView(R.layout.activity_doctor_registration);

        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.Email);
        Dob = (EditText) findViewById(R.id.Dob);
        Mob = (EditText) findViewById(R.id.Mob);
        Type = (EditText) findViewById(R.id.Type);
        Register_button = (Button) findViewById(R.id.save);
        logout_btn = (Button) findViewById(R.id.logout_btn);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSexGroup);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();


        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                 name = Name.getText().toString();
                email = Email.getText().toString();
                dob = Dob.getText().toString();
                mob = Mob.getText().toString();
                type = Type.getText().toString();
                gender = radioSexButton.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(!TextUtils.isEmpty(name)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(dob)||!TextUtils.isEmpty(mob)||!TextUtils.isEmpty(type)||!TextUtils.isEmpty(gender)) {


                    if (user != null) {
                        user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference docNames = FirebaseDatabase.getInstance().getReference().child("DocNames").child(user_id).child("Name");


                        Map newPost = new HashMap();
                        newPost.put("name", name);
                        newPost.put("email", email);
                        newPost.put("dob", dob);
                        newPost.put("mob", mob);
                        newPost.put("gender", gender);
                        newPost.put("id", user_id);
                        newPost.put("fees", null);
                        newPost.put("location", null);
                        newPost.put("time", null);
                        newPost.put("type", type);
                        newPost.put("avg_time", null);
                        newPost.put("clinic_name", null);
                        newPost.put("exp", null);
                        newPost.put("lat", null);
                        newPost.put("lon", null);
                        newPost.put("message", null);
                        newPost.put("maxPatient", null);

                        reference.child(type).child(user_id).setValue(newPost);

                        reference.child("DoctorList").child(user_id).child("Name").setValue(name); // put the name of the doctor under DocNames which contains all the doctors

                        Toast.makeText(DoctorRegistration.this, "You are successfully registered , Please tell us your details", Toast.LENGTH_SHORT).show();
                        Intent docHospitalDetails = new Intent(DoctorRegistration.this, DocHospitalDetails.class);
                        docHospitalDetails.putExtra("type", type);
                        startActivity(docHospitalDetails);
                        finish();


                    }
                }

                else{
                    Toast.makeText(DoctorRegistration.this, "Some of the fields are empty", Toast.LENGTH_SHORT).show();

                }
            }
        });


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendToAuth();
            }
        });

    }


    private void sendToAuth(){
        Intent authIntent = new Intent(DoctorRegistration.this,AuthActivity.class);
        startActivity(authIntent);
        finish();

    }

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        int id = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(id);

    }



    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            Intent authintent = new Intent(DoctorRegistration.this,AuthActivity.class);
            startActivity(authintent);



            finish();
        }




    }





}
