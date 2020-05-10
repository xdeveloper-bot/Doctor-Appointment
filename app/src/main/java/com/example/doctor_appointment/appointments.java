package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class appointments extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Integer intNum = 0;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        toolbar = findViewById(R.id.app_toolbar);
        floatingActionButton = findViewById(R.id.app_plus);
        mainLayout = findViewById(R.id.app_linearlayout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bookappointment.class));
                finish();
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String field = "appointments";
                if (documentSnapshot.get(field) != null){
                    Map<String, Map<String, String>> map = (Map) documentSnapshot.get(field);
                    //Log.d("documentSnapshot", map.toString());
                    //{app_15/4/2020_17:0={doctor=Dr.Bhimsen Garg, date=15/4/2020, time=17:0}, app_17/4/2020_17:0={doctor=Dr S J S Randhawa, date=17/4/2020, time=17:0}}
                    LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    for (Map.Entry m:map.entrySet()){
                        Map<String, String> map2 = (Map) m.getValue();
                        String docName = map2.get("doctor");
                        String date = map2.get("date");
                        String time = map2.get("time");
                        String dateTime = "Date: " + date + " Time: " + time;

                        // Create Multiple Card Layout
                        View tempView = li.inflate(R.layout.appointment_template, null);
                        TextView txtName = tempView.findViewById(R.id.atmp_name);
                        TextView txtDate = tempView.findViewById(R.id.atmp_time);
                        final TextView txtHospital = tempView.findViewById(R.id.atmp_hospital);
                        final TextView txtPhone = tempView.findViewById(R.id.atmp_phone);
                        TextView txtTouch = tempView.findViewById(R.id.atmp_touchTextView);
                        ImageView imgProfile = tempView.findViewById(R.id.atmp_profileImg);
                        ImageView imgDelete = tempView.findViewById(R.id.atmp_deleteImg);

                        // get hospital and number
                        fStore.collection("doctors")
                                .whereEqualTo("name", docName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot document : task.getResult()){
                                                txtHospital.setText(document.get("hospital").toString());
                                                txtPhone.setText(document.get("number").toString());
                                            }
                                        } else {
                                            Log.d("TAG", "Error getting documents: ",task.getException());
                                        }
                                    }
                                });

                        txtName.setText(docName);
                        txtDate.setText(dateTime);
                        txtTouch.setId(intNum);
                        txtTouch.setTag(docName);
                        txtTouch.setOnClickListener(btnTouchClicked);
                        imgProfile.setImageResource(R.drawable.doctor);
                        imgDelete.setImageResource(R.drawable.ic_delete);
                        imgDelete.setOnClickListener(btnDeleteClicked);
                        imgDelete.setId(intNum + 50);
                        imgDelete.setTag(docName);

                        mainLayout.addView(tempView);
                        intNum++;
                    }
                }
            }
        });
    }

    View.OnClickListener btnTouchClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), v.getId() + " " + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener btnDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), v.getId() + " " + v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

}
