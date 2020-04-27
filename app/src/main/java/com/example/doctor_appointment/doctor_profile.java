package com.example.doctor_appointment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class doctor_profile extends AppCompatActivity {
    TextView txtname,txtedit,txtreminder,txtprecription,txtshare,txtlogout;
    ImageView profilepic;
    FirebaseAuth dAuth;
    FirebaseFirestore dStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        profilepic=findViewById(R.id.dpro_profilepic);
        txtname=findViewById(R.id.dpro_name);
        txtedit=findViewById(R.id.dpro_edit);
        txtreminder=findViewById(R.id.dpro_reminder);
        txtprecription=findViewById(R.id.dpro_precription);
        txtshare=findViewById(R.id.dpro_share);
        txtlogout=findViewById(R.id.dpro_logout);

        dAuth =FirebaseAuth.getInstance();
        dStore =FirebaseFirestore.getInstance();
        userID = dAuth.getCurrentUser().getUid();

        DocumentReference documentReference = dStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
                // profilepic.setImageBitmap();
            }
        });


        txtedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), user_details.class));
            }
        });


        txtreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(doctor_profile.this,"Reminder clicked.",Toast.LENGTH_SHORT).show();

            }
        });


        txtprecription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(doctor_profile.this,"Prescription clicked.",Toast.LENGTH_SHORT).show();

            }
        });


        txtshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(doctor_profile.this,"Share",Toast.LENGTH_SHORT).show();
            }
        });


        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dAuth.signOut();
                startActivity(new Intent(getApplicationContext(), askdoctor.class));
                finish();
            }
        });


    }
}
