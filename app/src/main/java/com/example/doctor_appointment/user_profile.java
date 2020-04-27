package com.example.doctor_appointment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class user_profile extends AppCompatActivity {
    TextView txtname,txtedit,txtreminder,txtprecription,txtshare,txtlogout;
    ImageView profilepic;
    FirebaseAuth uAuth;
    FirebaseFirestore uStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profilepic=findViewById(R.id.upro_profilepic);
        txtname=findViewById(R.id.upro_name);
        txtedit=findViewById(R.id.upro_edit);
        txtreminder=findViewById(R.id.upro_reminder);
        txtprecription=findViewById(R.id.upro_precription);
        txtshare=findViewById(R.id.upro_share);
        txtlogout=findViewById(R.id.upro_logout);

        uAuth=FirebaseAuth.getInstance();
        uStore=FirebaseFirestore.getInstance();
        userID = uAuth.getCurrentUser().getUid();

        DocumentReference documentReference = uStore.collection("users").document(userID);
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
                startActivity(new Intent(getApplicationContext(),user_personal_details.class));
            }
        });


        txtreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(user_profile.this,"Reminder clicked.",Toast.LENGTH_SHORT).show();

            }
        });


        txtprecription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(user_profile.this,"Prescription clicked.",Toast.LENGTH_SHORT).show();

            }
        });


        txtshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(user_profile.this,"Share",Toast.LENGTH_SHORT).show();
            }
        });


        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uAuth.signOut();
                startActivity(new Intent(getApplicationContext(), askdoctor.class));
                finish();
            }
        });


    }
}
