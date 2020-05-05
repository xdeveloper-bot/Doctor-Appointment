package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class health_data extends AppCompatActivity {
    Button btnChange;
    TextView txtBp, txtSugar, txtDiabetes, txtOther;
    String valBp, valSugar, valDiabetes, valOther;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data);

        btnChange = findViewById(R.id.hdta_changeBtn);
        txtBp = findViewById(R.id.hdta_bp);
        txtSugar = findViewById(R.id.hdta_sugar);
        txtDiabetes = findViewById(R.id.hdta_diabetes);
        txtOther = findViewById(R.id.hdta_other);

        if (getIntent().getExtras() != null){
            valBp = getIntent().getExtras().getString("bp");
            valSugar = getIntent().getExtras().getString("sugar");
            valDiabetes = getIntent().getExtras().getString("diabetes");
            valOther = getIntent().getExtras().getString("other");
            txtBp.setText(valBp);
            txtSugar.setText(valSugar);
            txtDiabetes.setText(valDiabetes);
            txtOther.setText(valOther);
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), change_health_data.class));
            }
        });

    }
}
