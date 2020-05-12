package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class add_activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText txtName;
    TextView txtStart, txtStop;
    TimePicker timePicker;
    Button btnAdd, btnCancel, btnStartTime, btnStopTime;
    String time, userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String day = "default";

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

        if (getIntent().getExtras() != null) {
            day = getIntent().getExtras().getString("day");
            toolbar.setTitle("Add Activity : " + day);
        }

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

                Map<String, Object> time = new HashMap<>();
                time.put("name", Name);
                time.put("start", Start);
                time.put("stop", Stop);
                Map<String, Object> activity = new HashMap<>();
                activity.put("act_" + Name, time);
                Map<String, Object> days = new HashMap<>();
                days.put(day, activity);
                Map<String, Object> activities = new HashMap<>();
                activities.put("activities", days);

                fStore.collection("users").document(userID).set(activities, SetOptions.merge());
                Toast.makeText(getApplicationContext(), "Activity Added", Toast.LENGTH_SHORT).show();
                txtName.setText(null);
                txtStart.setText(null);
                txtStop.setText(null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), show_routine.class);
        i.putExtra("day", day);
        startActivity(i);
        finish();
    }
}
