package com.example.user.authentication;

import android.content.Intent;
import android.provider.ContactsContract;
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


public class UpComing extends AppCompatActivity {

    private Button cancel_appointment;
    String average_time ="";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);

        Intent intent = getIntent();
        if(intent != null && intent.getExtras()!=null) {
          average_time   = intent.getExtras().getString("average_time", "");
            Toast.makeText(UpComing.this,"average time is" + average_time ,Toast.LENGTH_SHORT).show();

        }

        mAuth = FirebaseAuth.getInstance();
        cancel_appointment = (Button)findViewById(R.id.cancel_appointment);

      final  DatabaseReference count = FirebaseDatabase.getInstance().getReference().child("Appointment");
        count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num = (int) dataSnapshot.getChildrenCount();
                Toast.makeText(UpComing.this,"appointment number is "+ num , Toast.LENGTH_SHORT).show();

                if(average_time!=null&&!average_time.equals("")){
                int average = Integer.parseInt(average_time);
                average = average*num;
                Toast.makeText(UpComing.this,"average time remaining is "+ average , Toast.LENGTH_SHORT).show();

                }

                String user_id = mAuth.getCurrentUser().getUid();
                count.child(user_id).child("app_num").setValue(num);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference removeChild = FirebaseDatabase.getInstance().getReference().child("Appointment").child(user_id);
                removeChild.removeValue();
                removeChild.removeValue();
                Intent home = new Intent(UpComing.this,Home.class);
                startActivity(home);
                finish();

            }
        });
    }
}
