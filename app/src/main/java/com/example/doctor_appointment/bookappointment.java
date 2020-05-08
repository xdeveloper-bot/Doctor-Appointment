package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class bookappointment extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookappointment);

        toolbar = findViewById(R.id.bapp_toolbar);
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
}
