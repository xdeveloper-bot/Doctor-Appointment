package com.example.doctor_appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class show_routine extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fBtnAdd;
    LinearLayout mainLayout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, day = "default", delAct;
    Integer intNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_routine);

        toolbar = findViewById(R.id.srtn_toolbar);
        fBtnAdd = findViewById(R.id.srtn_floatingBtn);
        mainLayout = findViewById(R.id.srtn_mainLinearLayout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        if (getIntent().getExtras() != null) {
            day = getIntent().getExtras().getString("day");
            toolbar.setTitle(day + " Activities");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), add_activity.class);
                i.putExtra("day", day);
                startActivity(i);
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String field = "activities." + day;
                if (documentSnapshot.get(field) != null) {
                    Map<String, Map<String, String>> map = (Map) documentSnapshot.get(field);
                    LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    for (Map.Entry m : map.entrySet()) {
                        Map<String, String> map2 = (Map) m.getValue();
                        String name = map2.get("name");
                        String start = map2.get("start");
                        String stop = map2.get("stop");

                        // Create multiple Card Layout
                        View tempView = li.inflate(R.layout.show_routine_templete, null);
                        TextView txtName = tempView.findViewById(R.id.srtmp_name);
                        TextView txtStart = tempView.findViewById(R.id.srtmp_startTime);
                        TextView txtStop = tempView.findViewById(R.id.srtmp_endTime);
                        ImageView imgCalender = tempView.findViewById(R.id.srtmp_calenderimg);
                        ImageView imgDelete = tempView.findViewById(R.id.srtmp_delete);
                        Switch switchOn = tempView.findViewById(R.id.srtmp_switch);

                        txtName.setText(name);
                        txtStart.setText(start);
                        txtStop.setText(stop);
                        imgCalender.setImageResource(R.drawable.ic_date);
                        imgDelete.setImageResource(R.drawable.ic_delete);
                        imgDelete.setOnClickListener(btnDelete);
                        imgDelete.setTag(name);
                        switchOn.setOnClickListener(btnSwitch);
                        switchOn.setTag("switch" + intNum);

                        mainLayout.addView(tempView);
                        intNum++;
                    }
                }
            }
        });
    }

    View.OnClickListener btnDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            delAct = "activities." + day + ".act_" + v.getTag().toString();
            Map<String, Object> delActivity = new HashMap<>();
            delActivity.put(delAct, FieldValue.delete());

            fStore.collection("users").document(userID).update(delActivity);
            finish();
            startActivity(getIntent());
        }
    };

    View.OnClickListener btnSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), v.getId() + " " + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

}
