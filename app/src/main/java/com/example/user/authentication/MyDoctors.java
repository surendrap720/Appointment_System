package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MyDoctors extends AppCompatActivity {
    String docId = "";
    String docView = "";
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    String user_id = "";
    String doctorId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctors);
        getSupportActionBar().setTitle("My Doctors");
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("MyDoctors").child(user_id);

      /*  Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            docId = intent.getExtras().getString("doctorId", "");
            docView = intent.getExtras().getString("doctorType", "");

            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();
        }*/

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerAdapter<favDoctors, MyDoctors.DocViewHolder> adapter = new FirebaseRecyclerAdapter<favDoctors, MyDoctors.DocViewHolder>(
                favDoctors.class,
                R.layout.individual_favourite_doctor,
                MyDoctors.DocViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(final MyDoctors.DocViewHolder viewHolder, final favDoctors model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setType(model.getType());
                viewHolder.setClinicName(model.getClinicName());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setFees(model.getFees());
                viewHolder.setTime(model.getTime());
                doctorId =  model.getId();


                // not required  viewHolder.setId(id);

                viewHolder.save.setOnClickListener(new View.OnClickListener() {//for mydoctor
                    @Override
                    public void onClick(View view) {
                        reference.child(doctorId).removeValue();

                        Intent intent = new Intent(MyDoctors.this,MyDoctors.class);
                        startActivity(intent);

                    }
                });



            }
        };


        recyclerView.setAdapter(adapter);



    }

    public static class DocViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Type;
        TextView ClinicName;
        TextView Location;
        TextView Experience;
        TextView Fees;
        TextView Time;
        private Button save;

        //nq  TextView Id;
        // TextView Location;
        private CardView card;

        public DocViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Fees = (TextView) itemView.findViewById(R.id.Fees);
            Time = (TextView) itemView.findViewById(R.id.Time);
            save = (Button) itemView.findViewById(R.id.save);
            Type = (TextView) itemView.findViewById(R.id.Specialization);
            ClinicName = (TextView) itemView.findViewById(R.id.ClinicName);
            Location = (TextView) itemView.findViewById(R.id.Location);
            Experience = (TextView) itemView.findViewById(R.id.Experience);
            //nq    Id = (TextView) itemView.findViewById(R.id.Id);
            //  Location = (TextView) itemView.findViewById(R.id.Location);


        }

        public void setName(String name) {

            Name.setText("Dr. " + name);
        }

        public void setFees(String fees) {

            Fees.setText("Rs. " + fees);
        }


        public void setTime(String time) {

            Time.setText("Time: " + time);
        }

        public void setType(String type) {
            Type.setText(type);
        }

        public void setClinicName(String clinic_name) {
            ClinicName.setText(clinic_name);
        }

        public void setLocation(String location) {

            Location.setText(location);
        }




      /*nq  public void setId(String id) {
            Id.setText("Id "+ id);
        }*/

    }
}
