package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

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
        btnsubmit = findViewById(R.id.submit);
        ratingBar = findViewById(R.id.rating);
    }

    public void onBtnClick(View v){
        float ratingvalue= ratingBar.getRating();
        Toast.makeText(rating.this, "Rating is:"+ratingvalue,Toast.LENGTH_SHORT).show();
    }
}
