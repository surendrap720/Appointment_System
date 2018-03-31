package com.example.user.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MyDoctors extends AppCompatActivity {
    String docId = "";
    String docView = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doctors);



        Intent intent = getIntent();
        if(intent != null&& intent.getExtras()!=null) {
            docId = intent.getExtras().getString("doctorId", "");
            docView = intent.getExtras().getString("doctorType", "");

            //Toast.makeText(Confirm.this,"docId is"+docId,Toast.LENGTH_SHORT).show();
        }
    }
}
