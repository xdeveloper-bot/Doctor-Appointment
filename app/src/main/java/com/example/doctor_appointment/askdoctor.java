package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class askdoctor extends AppCompatActivity {
    public Button btn1, btn2;

    public void init(){

        btn1= (Button)findViewById(R.id.doctor_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doc = new Intent(askdoctor.this, doctorlogin.class);
                startActivity(doc);
            }
        });

        btn2= (Button)findViewById(R.id.patient_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(askdoctor.this, userlogin.class);
                startActivity(user);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askdoctor);
        init();
    }
}
