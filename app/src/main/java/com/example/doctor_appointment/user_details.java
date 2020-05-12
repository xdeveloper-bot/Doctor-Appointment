package com.example.doctor_appointment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class user_details extends AppCompatActivity {
    EditText txtname, txtemail, txtdob, txtaddress, txtstate, txtzip;
    DatePicker txtdatepicker;
    Button btnsubmit;
    ProgressBar progress;
    FirebaseAuth uAuth;
    FirebaseFirestore uStore;
    String userID;
    ImageView profileimage, calenderimg;
    TextView txtselectdate;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        txtname = findViewById(R.id.udtl_name);
        txtemail = findViewById(R.id.udtl_email);
        txtdob = findViewById(R.id.udtl_dob);
        txtaddress = findViewById(R.id.udtl_address);
        txtstate = findViewById(R.id.udtl_state);
        txtzip = findViewById(R.id.udtl_zip);
        calenderimg = findViewById(R.id.udtl_calender);
        txtdatepicker = findViewById(R.id.udtl_datepicker);
        txtselectdate = findViewById(R.id.udtl_selectdate);
        progress = findViewById(R.id.udtl_progressBar);
        btnsubmit = findViewById(R.id.udtl_submitBtn);

        uAuth = FirebaseAuth.getInstance();
        uStore = FirebaseFirestore.getInstance();
        userID = uAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + userID + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimage);
            }
        });

        profileimage = findViewById(R.id.udtl_profileimg);

        final DocumentReference documentReference = uStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
                txtemail.setText(documentSnapshot.getString("email"));
            }
        });

        calenderimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdatepicker.setVisibility(View.VISIBLE);
                txtselectdate.setVisibility(View.VISIBLE);
                btnsubmit.setVisibility(View.GONE);
                Calendar calender = Calendar.getInstance();
                txtdatepicker.init(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),
                        calender.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = txtdatepicker.getDayOfMonth() + "-" + txtdatepicker.getMonth() + "-" + txtdatepicker.getYear();
                                txtdob.setText(date);
                            }
                        });
            }
        });

        txtselectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdatepicker.setVisibility(View.GONE);
                btnsubmit.setVisibility(View.VISIBLE);
                txtselectdate.setVisibility(View.GONE);
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = txtname.getText().toString();
                final String email = txtemail.getText().toString().trim();
                final String dob = txtdob.getText().toString().trim();
                final String address = txtaddress.getText().toString().trim();
                final String state = txtstate.getText().toString().trim();
                final String zip = txtzip.getText().toString().trim();

                progress.setVisibility(View.VISIBLE);

                Map<String, Object> user = new HashMap<>();
                user.put("name", name);
                user.put("email", email);
                user.put("dob", dob);
                user.put("address", address);
                user.put("state", state);
                user.put("zip", zip);
                documentReference.update(user);

                Toast.makeText(user_details.this, "Profile Created.", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Verification code sended to your email id. Please verify and then login.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), user_login.class));
                finish();

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
                uploadImageToFirebase(imageuri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageuri) {
        //upload image to fire base storage
        final StorageReference fileref = storageReference.child("users/" + userID + "/profile.jpg");
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
                Toast.makeText(user_details.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
