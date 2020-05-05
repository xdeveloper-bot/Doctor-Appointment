package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class add_activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText txtName, txtStart, txtStop;
    TimePicker timePicker;
    Button btnAdd, btnCancel, btnStartTime, btnStopTime;
    String time, userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        toolbar = findViewById(R.id.aact_toolbar);
        txtName = findViewById(R.id.aact_name);
        txtStart = findViewById(R.id.aact_start);
        txtStop = findViewById(R.id.aact_stop);
        timePicker = findViewById(R.id.aact_timePicker);
        btnStartTime = findViewById(R.id.aact_startBtn);
        btnStopTime = findViewById(R.id.aact_stopBtn);
        btnAdd = findViewById(R.id.aact_acceptBtn);
        btnCancel = findViewById(R.id.aact_cancelBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.VISIBLE);
                btnStartTime.setVisibility(View.VISIBLE);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + ":" + minute;
                    }
                });
            }
        });

        txtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setVisibility(View.VISIBLE);
                btnStopTime.setVisibility(View.VISIBLE);
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + ":" + minute;
                    }
                });
            }
        });

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtStart.setText(time);
                timePicker.setVisibility(View.GONE);
                btnStartTime.setVisibility(View.GONE);
            }
        });

        btnStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtStop.setText(time);
                timePicker.setVisibility(View.GONE);
                btnStopTime.setVisibility(View.GONE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), show_routine.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = txtName.getText().toString().trim();
                String Start = txtStart.getText().toString().trim();
                String Stop = txtStop.getText().toString().trim();
                if (TextUtils.isEmpty(Name)) {
                    txtName.setError("Title is Required.");
                    return;
                }
                if (TextUtils.isEmpty(Start)) {
                    txtStart.setError("Start time is required.");
                    return;
                }
                if (TextUtils.isEmpty(Stop)) {
                    txtStop.setError("Stop time is required.");
                    return;
                }

                Map<String, Object> act = new HashMap<>();
                act.put("act_name", Name);
                act.put("start", Start);
                act.put("stop", Stop);
                Map<String, Object> time = new HashMap<>();
                time.put("activity_" + Name, act);

                fStore.collection("users").document(userID).update(time);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
