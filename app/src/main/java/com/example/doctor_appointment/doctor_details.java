package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class doctor_details extends AppCompatActivity {
    EditText txtname,txtemail,txtaddress,txtspecialty,txtmobile;
    Button btnsubmit;
    ProgressBar progress;
    FirebaseAuth dAuth;
    FirebaseFirestore dStore;
    String userID;
    ImageView profileimage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        txtname=findViewById(R.id.ddtl_name);
        txtemail=findViewById(R.id.ddtl_email);
        txtaddress=findViewById(R.id.ddtl_address);
        txtspecialty=findViewById(R.id.ddtl_speciality);
        txtmobile=findViewById(R.id.ddtl_mobile);
        progress=findViewById(R.id.ddtl_progressBar);
        btnsubmit=findViewById(R.id.ddtl_submitbtn);

        dAuth=FirebaseAuth.getInstance();
        dStore=FirebaseFirestore.getInstance();

        userID = dAuth.getCurrentUser().getUid();

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+ userID +"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimage);
            }
        });

        profileimage = findViewById(R.id.ddtl_profilepic);

        final DocumentReference documentReference = dStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
                txtemail.setText(documentSnapshot.getString("email"));
                txtmobile.setText(documentSnapshot.getString("mobile"));
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = txtname.getText().toString();
                final String email = txtemail.getText().toString().trim();
                final String mobile = txtmobile.getText().toString().trim();
                final String address = txtaddress.getText().toString().trim();
                final String specialty = txtspecialty.getText().toString().trim();

                progress.setVisibility(View.VISIBLE);

                Map<String,Object> user = new HashMap<>();
                user.put("name", name);
                user.put("email",email);
                user.put("mobile",mobile);
                user.put("address",address);
                user.put("specialty",specialty);
                documentReference.set(user);

                Toast.makeText(doctor_details.this,"Profile Created.",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(), doctor_dashboard.class));

            }
        });

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //profile change
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageuri = data.getData();
                // profileimage.setImageURI(imageuri);

                uploadImageToFirebase(imageuri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageuri) {
        //upload image to fire base storage
        final StorageReference fileref = storageReference.child("users/"+dAuth.getCurrentUser().getUid()+"profile.jpg");
        fileref.putFile(imageuri).addOnSuccessListener((new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileimage);
                    }
                });
            }
        })).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(doctor_details.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
