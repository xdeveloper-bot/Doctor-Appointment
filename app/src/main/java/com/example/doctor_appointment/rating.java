package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class rating extends AppCompatActivity {

    Button btnsubmit;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        btnsubmit = findViewById(R.id.rat_submit);
        ratingBar = findViewById(R.id.rat_star);

    }


    public void onBtnClick(View v){
        float ratingvalue= ratingBar.getRating();
        Toast.makeText(rating.this, "Rating is:"+ratingvalue,Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), setting.class));
        finish();

    }



}
