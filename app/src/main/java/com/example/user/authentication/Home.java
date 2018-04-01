package com.example.user.authentication;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button physician;
    private Button dentist;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Home");
        setContentView(R.layout.activity_home);


        mDrawerlayout=(DrawerLayout)findViewById(R.id.DrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        physician = (Button) findViewById(R.id.physician);
        dentist = findViewById(R.id.dentist);

        physician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String controller = physician.getText().toString();
                Toast.makeText(Home.this,"you clicked "+controller,Toast.LENGTH_SHORT).show();
                Intent doctor = new Intent(Home.this,Doctor.class);
                doctor.putExtra("physician",controller);
                startActivity(doctor);

            }
        });

        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String controller = dentist.getText().toString();
                Toast.makeText(Home.this,"you clicked "+controller,Toast.LENGTH_SHORT).show();
                Intent doctor = new Intent(Home.this,Doctor.class);
                doctor.putExtra("physician",controller);
                startActivity(doctor);

            }
        });

    }


  /*  public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(Home.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                }
            });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

            return super.onOptionsItemSelected(item);
        }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.Logout){
            mAuth.signOut();
            sendToAuth();
            Toast.makeText(this,"You have logged out",Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.MyProfile){

            Intent myprofile = new Intent(Home.this,MyProfile.class);
            startActivity(myprofile);
           // finish();

        }

        else if(id==R.id.MyDoctors){


            Intent mydoctors = new Intent(Home.this,MyDoctors.class);
            startActivity(mydoctors);

        }

        else if(id==R.id.History){
            Intent history = new Intent(Home.this,History.class);
            startActivity(history);

        }

        else if(id==R.id.UpComing){
            Intent upcoming = new Intent(Home.this,UpComing.class);
            startActivity(upcoming);

        }

        else if(id==R.id.Settings){
            Intent settings = new Intent(Home.this,Settings.class);
            startActivity(settings);

        }
        else if(id==R.id.FAQ){
            Intent faq = new Intent(Home.this,Faq.class);
            startActivity(faq);

        }
        else if(id==R.id.AboutUs){

            Intent aboutus = new Intent(Home.this,AboutUs.class);
            startActivity(aboutus);


        }
        else{

        }

        return false;
    }
    private void sendToAuth() {
        Intent authIntent = new Intent(Home.this, AuthActivity.class);
        authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(authIntent);
        finish();
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(" Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

}
