package com.example.doctor_appointment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class show_routine extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fBtnAdd;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, day = "default";
    Integer intNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_routine);

        toolbar = findViewById(R.id.srtn_toolbar);
        fBtnAdd = findViewById(R.id.srtn_floatingBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        if (getIntent().getExtras() != null){
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
                if (documentSnapshot.get(field) != null){
                    Map<String, Map<String, String>> map = (Map) documentSnapshot.get(field);
                    //Log.d("TAG", "Class : " + map.getClass().toString() + "Data : " + map.toString());
                    LinearLayout mainLayout = (LinearLayout) findViewById(R.id.srtn_mainLinearLayout);
                    LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    for (Map.Entry m:map.entrySet()){
                        //Log.d("TAG", "Key : " + m.getKey().toString() + " Value : " + m.getValue().toString());

                        Map <String, String> map2 = (Map) m.getValue();
                        String name = map2.get("act_name");
                        String start = map2.get("start");
                        String stop = map2.get("stop");

                        // Create multiple Card Layout
                        View tempView = li.inflate(R.layout.show_routine_templete, null);
                        TextView txtName = (TextView) tempView.findViewById(R.id.srtmp_name);
                        TextView txtStart = (TextView) tempView.findViewById(R.id.srtmp_startTime);
                        TextView txtStop = (TextView) tempView.findViewById(R.id.srtmp_endTime);
                        ImageView imgCalender = (ImageView) tempView.findViewById(R.id.srtmp_calenderimg);
                        ImageView imgDelete = (ImageView) tempView.findViewById(R.id.srtmp_delete);
                        Switch switchOn = (Switch) tempView.findViewById(R.id.srtmp_switch);

                        txtName.setText(name);
                        txtStart.setText(start);
                        txtStop.setText(stop);
                        imgCalender.setImageResource(R.drawable.ic_date);
                        imgDelete.setImageResource(R.drawable.ic_delete);
                        imgDelete.setOnClickListener(btnDelete);
                        imgDelete.setTag("delete" + intNum);
                        switchOn.setOnClickListener(btnSwitch);
                        switchOn.setTag("switch" + intNum);

                        intNum++;
                        mainLayout.addView(tempView);
                    }
                }
            }
        });
    }

    View.OnClickListener btnDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*switch (v.getTag()){
                case "delete0":
                    // action
                    break;
                case "delete1":
                    // ac
                    break;
                default:
                    break;
            }*/
            Toast.makeText(getApplicationContext(), v.getId() + " "+ v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener btnSwitch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*switch (v.getTag()){
                case "delete0":
                    // action
                    break;
                case "delete1":
                    // ac
                    break;
                default:
                    break;
            }*/
            Toast.makeText(getApplicationContext(), v.getId() + " "+ v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

}
