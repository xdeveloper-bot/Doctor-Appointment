package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class bookappointment extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtwomen, txtskin, txtgeneral, txtchild, txtdental, txteye, txtmental, txtbrain, txtheart, txtcancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookappointment);

        toolbar = findViewById(R.id.bapp_toolbar);
        txtwomen = findViewById(R.id.bapp_women);
        txtskin = findViewById(R.id.bapp_skin);
        txtgeneral = findViewById(R.id.bapp_general);
        txtchild = findViewById(R.id.bapp_child);
        txtdental = findViewById(R.id.bapp_dental);
        txteye = findViewById(R.id.bapp_eye);
        txtmental = findViewById(R.id.bapp_mental);
        txtheart = findViewById(R.id.bapp_heart);
        txtcancer = findViewById(R.id.bapp_cancer);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void btnWomenClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Women");
        startActivity(i);
    }

    public void btnSkinClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Skin");
        startActivity(i);
    }

    public void btnGeneralClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "General");
        startActivity(i);
    }

    public void btnChildClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Child");
        startActivity(i);
    }

    public void btnDentalClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Dental");
        startActivity(i);
    }

    public void btnEyeClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Eye");
        startActivity(i);
    }

    public void btnMentalClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Mental");
        startActivity(i);
    }

    public void btnBrainClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Brain");
        startActivity(i);
    }

    public void btnHeartClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Heart");
        startActivity(i);
    }

    public void btnCancerClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", "Cancer");
        startActivity(i);
    }

}
