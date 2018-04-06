package com.example.user.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Doctor extends AppCompatActivity {
    // private ListView lst;
    // private ArrayList<String> username = new ArrayList<>();
    // private ArrayList<DoctorModel> doctorDetails = new ArrayList<>();
    String docView = "";
    private RecyclerView recyclerView;

    String id = "";
    private FirebaseAuth mAuth;
    String user_id ="";
    String type = "";
    String name = "";
    String clinicName = "";
    String location = "";
    String fees = "";
    String time = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Doctors");
        setContentView(R.layout.activity_doctor);
        mAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        if(intent != null&& intent.getExtras()!=null) {
          docView   = intent.getExtras().getString("physician", "");
            //Toast.makeText(Doctor.this,"docView is"+docView,Toast.LENGTH_SHORT).show();
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(docView);
        user_id = mAuth.getCurrentUser().getUid();


        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerAdapter<DocDetail, Doctor.DocViewHolder> adapter = new FirebaseRecyclerAdapter<DocDetail, Doctor.DocViewHolder>(
                DocDetail.class,
                R.layout.individual_doctor,
                Doctor.DocViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(final Doctor.DocViewHolder viewHolder, final DocDetail model, int position) {

                viewHolder.setName(model.getName());
                name = model.getName();
                viewHolder.setType(model.getType());
                type = model.getType();
                viewHolder.setClinic_name(model.getClinic_name());
                clinicName = model.getClinic_name();
                viewHolder.setLocation(model.getLocation());
                location = model.getLocation();
                viewHolder.setFees(model.getFees());
                fees = model.getFees();
                viewHolder.setTime(model.getTime());
                time = model.getTime();
                viewHolder.setExperience(model.getExperience());




                  id = model.getId();
              // not required  viewHolder.setId(id);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(Doctor.this,"clicked",Toast.LENGTH_SHORT).show();
                        Intent doctorProfile = new Intent(Doctor.this,Confirm.class);
                        doctorProfile.putExtra("docId" ,id);
                        doctorProfile.putExtra("docView",docView);
                      //  doctorProfile.putExtra("docName" ,id);
                        startActivity(doctorProfile);
                       // finish();
                    }
                });

                viewHolder.save.setOnClickListener(new View.OnClickListener() {//for mydoctor
                    @Override
                    public void onClick(View view) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("MyDoctors").child(user_id).child(id);
                        Map newPost = new HashMap();
                        newPost.put("name",name);
                        newPost.put("id",id);
                        newPost.put("type",type);
                        newPost.put("clinicName",clinicName);
                        newPost.put("location",location);
                        newPost.put("fees",fees);
                        newPost.put("time",time);
                        ref.setValue(newPost);
                        Toast.makeText(Doctor.this,"Doctor has been saved as favourites",Toast.LENGTH_SHORT).show();

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
            Type = (TextView) itemView.findViewById(R.id.Specialization);
            ClinicName = (TextView) itemView.findViewById(R.id.ClinicName);
            Location = (TextView) itemView.findViewById(R.id.Location);
            Experience = (TextView) itemView.findViewById(R.id.Experience);
            save = (Button)itemView.findViewById(R.id.save);

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

        public void setClinic_name(String clinic_name) {
            ClinicName.setText(clinic_name);
        }

        public void setLocation(String location) {

            Location.setText(location);
        }

        public void setExperience(String experience) {
            Experience.setText(experience);
        }



      /*nq  public void setId(String id) {
            Id.setText("Id "+ id);
        }*/

    }

}
      //  lst = (ListView) findViewById(R.id.listview);

       //  final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Doctors");


      // final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, username);
       // lst.setAdapter(arrayAdapter);


       /* current_user_db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                DoctorList doctor =(DoctorList)dataSnapshot.getValue(DoctorList.class);
                String doctorString = String.valueOf(doctor);
               // arrayAdapter.add(doctorString);
                username.add(doctor.getName());
                //String docid = doctor.getId();

               arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

      /*  lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;

                Intent confirm2 = new Intent(Doctor.this, Confirm.class);
               confirm2.putExtra("DocName", username.get(position));
                 startActivity(confirm2);

            }

        });*/



