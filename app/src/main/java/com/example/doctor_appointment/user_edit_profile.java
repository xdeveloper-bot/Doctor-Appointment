package com.example.doctor_appointment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class user_edit_profile extends AppCompatActivity {
    ImageView profileimg;
    Button btnchange,btnreset;
    TextView txtname,txtemail,txtmobile,txtaddress;
    FirebaseAuth uAuth;
    FirebaseFirestore fstore;
    StorageReference storageReference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);

        profileimg=findViewById(R.id.uedpro_img);
        btnchange=findViewById(R.id.uedpro_changebtn);
        btnreset=findViewById(R.id.uedpro_resetbtn);
        txtname=findViewById(R.id.uedpro_name);
        txtemail=findViewById(R.id.uedpro_email);
        txtmobile=findViewById(R.id.uedpro_mobile);
        txtaddress=findViewById(R.id.uedpro_address);

        uAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        userID=uAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
                txtemail.setText(documentSnapshot.getString("email"));
                txtmobile.setText(documentSnapshot.getString("mobile"));
                txtaddress.setText(documentSnapshot.getString("address"));
            }
        });

        StorageReference profileRef = storageReference.child("users/"+ userID +"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimg);
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),user_details.class));

            }
        });


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset Password code here      <--


            }
        });


    }
}