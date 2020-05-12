package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class health_data extends AppCompatActivity {
    Button btnChange;
    Toolbar toolbar;
    TextView txtBp, txtSugar, txtDiabetes, txtOther;
    String valBp, valSugar, valDiabetes, valOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data);

        toolbar = findViewById(R.id.hdta_toolbar);
        btnChange = findViewById(R.id.hdta_changeBtn);
        txtBp = findViewById(R.id.hdta_bp);
        txtSugar = findViewById(R.id.hdta_sugar);
        txtDiabetes = findViewById(R.id.hdta_diabetes);
        txtOther = findViewById(R.id.hdta_other);

        if (getIntent().getExtras() != null) {
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
