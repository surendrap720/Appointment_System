package com.example.user.authentication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAppointment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        mDrawerlayout=(DrawerLayout)findViewById(R.id.DrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();


    }


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
        if (id == R.id.Logout) {
            mAuth.signOut();
            sendToAuth();
            Toast.makeText(this, "You have logged out", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.MyProfile) {

            Intent myprofile = new Intent(ViewAppointment.this, MyProfile.class);
            startActivity(myprofile);
            finish();

        } else if (id == R.id.MyDoctors) {


            Intent mydoctors = new Intent(ViewAppointment.this, MyDoctors.class);
            startActivity(mydoctors);

        } else if (id == R.id.History) {
            Intent history = new Intent(ViewAppointment.this, History.class);
            startActivity(history);

        } else if (id == R.id.UpComing) {
            Intent upcoming = new Intent(ViewAppointment.this, UpComing.class);
            startActivity(upcoming);

        } else if (id == R.id.Settings) {
            Intent settings = new Intent(ViewAppointment.this, Settings.class);
            startActivity(settings);

        } else if (id == R.id.FAQ) {
            Intent faq = new Intent(ViewAppointment.this, Faq.class);
            startActivity(faq);

        } else if (id == R.id.AboutUs) {

            Intent aboutus = new Intent(ViewAppointment.this, AboutUs.class);
            startActivity(aboutus);


        } else {

        }

        return false;
    }

    private void sendToAuth() {
        Intent authIntent = new Intent(ViewAppointment.this, AuthActivity.class);
        authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(authIntent);
        finish();
    }


}
