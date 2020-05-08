package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void btnClicked(View view){
        Intent i = new Intent(this, doctor_list.class);
        switch (view.getId()){
            case R.id.bapp_skin:
                i.putExtra("value", "Skin and Hair");
                break;
            case R.id.bapp_women:
                i.putExtra("value", "Women's Health");
                break;
            case R.id.bapp_child:
                i.putExtra("value", "Child Specialist");
                break;
            case R.id.bapp_general:
                i.putExtra("value", "General Physician");
                break;
            case R.id.bapp_eye:
                i.putExtra("value", "Eye Specialist");
                break;
            case R.id.bapp_dental:
                i.putExtra("value", "Dental Care");
                break;
            case R.id.bapp_brain:
                i.putExtra("value", "Brain and Nerves");
                break;
            case R.id.bapp_mental:
                i.putExtra("value", "Mental Wellness");
                break;
            case R.id.bapp_heart:
                i.putExtra("value", "Heart");
                break;
            case R.id.bapp_cancer:
                i.putExtra("value", "Cancer");
                break;
        }
        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /*public void btnWomenClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtwomen.getText());
        startActivity(i);
    }

    public void btnSkinClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtskin.getText());
        startActivity(i);
    }

    public void btnGeneralClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtgeneral.getText());
        startActivity(i);
    }

    public void btnChildClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtchild.getText());
        startActivity(i);
    }

    public void btnDentalClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtdental.getText());
        startActivity(i);
    }

    public void btnEyeClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txteye.getText());
        startActivity(i);
    }

    public void btnMentalClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtmental.getText());
        startActivity(i);
    }

    public void btnBrainClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtbrain.getText());
        startActivity(i);
    }

    public void btnHeartClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtheart.getText());
        startActivity(i);
    }

    public void btnCancerClickAct(View v) {
        Intent i = new Intent(this, doctor_list.class);
        i.putExtra("value", txtcancer.getText());
        startActivity(i);
    }*/

}
