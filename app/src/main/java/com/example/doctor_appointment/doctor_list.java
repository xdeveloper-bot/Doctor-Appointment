package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class doctor_list extends AppCompatActivity {
    EditText txtsearch;
    String valFromBookAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        txtsearch = findViewById(R.id.dlst_searchtxt);
        valFromBookAppointment = getIntent().getExtras().getString("value");
        txtsearch.setText(valFromBookAppointment);



    }
}
