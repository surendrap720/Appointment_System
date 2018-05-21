package com.example.user.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button skip;
    private EditText Name;
    private EditText Email;
    private EditText Dob;
    private EditText Mob;
    private CircleImageView profileImage;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button Register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Registration");
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        skip = (Button) findViewById(R.id.logout_btn);
        Name = (EditText) findViewById(R.id.Name);
        Email = (EditText) findViewById(R.id.Email);
        Dob = (EditText) findViewById(R.id.Dob);
        Mob = (EditText) findViewById(R.id.Mob);
        Register_button = (Button) findViewById(R.id.save);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSexGroup);
        profileImage =(CircleImageView) findViewById(R.id.profileImage);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                     //   BringImagePicker();
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }

                } else {

                  //  BringImagePicker();

                }

            }

        });

        Register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String name = Name.getText().toString();
               final String email = Email.getText().toString();
               final String dob = Dob.getText().toString();
                final String mob = Mob.getText().toString();
                final String gender = radioSexButton.getText().toString();

                //details are empty
                if((name.length()==0)||(email.length()==0)||(dob.length()==0)||mob.length()==0){
                    Toast.makeText(MainActivity.this,"Fields are Empty",Toast.LENGTH_SHORT).show();
                }

                else if(mob.length()!=0){

                    Toast.makeText(MainActivity.this,"Invalid ContactNumber",Toast.LENGTH_SHORT).show();


                }
                else{

                    Query emailquery = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("email").equalTo(email);
                    emailquery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                Toast.makeText(MainActivity.this, "email id is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    // User is signed in
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
                                    Map newPost = new HashMap();
                                    newPost.put("name", name);
                                    newPost.put("email", email);
                                    newPost.put("dob", dob);
                                    newPost.put("mob", mob);
                                    newPost.put("gender", gender);
                                    newPost.put("appointment",null);


                                    current_user_db.setValue(newPost);
                                    Toast.makeText(MainActivity.this, "You are successfully registered.", Toast.LENGTH_SHORT).show();

                                    Intent mainIntent = new Intent(MainActivity.this,Home.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        int id = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(id);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
           Intent authintent = new Intent(MainActivity.this,AuthActivity.class);
            startActivity(authintent);
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
