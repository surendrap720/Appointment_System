package com.example.user.authentication;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class ViewAppointment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private Button start;
    private Button stop;
    private Button message;
    private Button send_button;
    private EditText messageEditText;
    private String startFormat="";
    private String stopFormat="";
    private String startHr="";
    private String startMm="";
    private String stopHr="";
    private String stopMm="";
    private String user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        start = findViewById(R.id.start_button);
        stop = findViewById(R.id.stop_button);
        message = findViewById(R.id.message_button);
        messageEditText = findViewById(R.id.messageEditText);
        send_button = findViewById(R.id.send_button);

        mDrawerlayout=(DrawerLayout)findViewById(R.id.DrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm");
                startFormat = simpleDateFormat.format(new Date());
                startHr=startFormat.substring(0,2);
                startMm = startFormat.substring(3);


               // Toast.makeText(ViewAppointment.this,"Date is "+startTime.substring(0,2),Toast.LENGTH_SHORT).show();
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

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messageEditText.setVisibility(View.VISIBLE);
                send_button.setVisibility(View.VISIBLE);

            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String impMessage = messageEditText.getText().toString();
                Toast.makeText(ViewAppointment.this,"Important message is "+impMessage,Toast.LENGTH_SHORT).show();
                messageEditText.setVisibility(View.INVISIBLE);
                send_button.setVisibility(View.INVISIBLE);
                setMessage(impMessage);

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

                viewHolder.setPatient_name(model.getPatient_name());


            }

        };

        recyclerView.setAdapter(adapter);
    }

    private void setMessage(String impMessage){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Doctors").child(user_id);
       ref.child("message").setValue(impMessage);

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
        private CardView card;


        public DocViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            Name = (TextView) itemView.findViewById(R.id.Name);

        }

        public void setPatient_name(String patient_name) {
            Name.setText(patient_name);
        }
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

            Intent myprofile = new Intent(ViewAppointment.this, DocProfile.class);
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
