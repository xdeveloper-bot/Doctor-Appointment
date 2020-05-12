package com.example.doctor_appointment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class user_profile extends AppCompatActivity {
    TextView txtname, txtedit, txtreminder, txtprecription, txtshare, txtlogout;
    FirebaseAuth uAuth;
    FirebaseFirestore uStore;
    Toolbar toolbar;
    String userID;
    ImageView profileimage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileimage = findViewById(R.id.upro_profilepic);
        txtname = findViewById(R.id.upro_name);
        txtedit = findViewById(R.id.upro_edit);
        txtreminder = findViewById(R.id.upro_reminder);
        txtprecription = findViewById(R.id.upro_precription);
        txtshare = findViewById(R.id.upro_share);
        txtlogout = findViewById(R.id.upro_logout);
        toolbar = findViewById(R.id.upro_toolbar);

        storageReference = FirebaseStorage.getInstance().getReference();
        uAuth = FirebaseAuth.getInstance();
        uStore = FirebaseFirestore.getInstance();
        userID = uAuth.getCurrentUser().getUid();

        DocumentReference documentReference = uStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
            }
        });

        StorageReference profileRef = storageReference.child("users/" + userID + "profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimage);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), user_edit_profile.class));
            }
        });

        txtreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(user_profile.this, "Reminder clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        txtprecription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(user_profile.this, "Prescription clicked.", Toast.LENGTH_SHORT).show();
            }
        });

        txtshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(user_profile.this, "Share", Toast.LENGTH_SHORT).show();
            }
        });

        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
