package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Map;

public class show_routine extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fBtnAdd;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, day = "default";

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

        /*fStore.collection("users").document(userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            Log.d("TAG", task.getResult().toString());
                        } else {
                            Log.d("TAG", "Error getting documents: ",task.getException());
                        }
                    }
                });*/

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String field = "activities." + day;
                Log.d("TAG", documentSnapshot.get(field).toString());
                // D/TAG: {act_one={act_name=one, stop=2:14, start=1:1}}
                // D/TAG: {act_one={stop=2:50, start=2:20, name=one}, act_two={act_name=two, stop=2:10, start=1:30}}



            }
        });

    }
}
