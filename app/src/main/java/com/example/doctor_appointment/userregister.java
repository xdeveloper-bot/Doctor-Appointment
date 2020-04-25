package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class userregister extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button btnregister;
    EditText txtname, txtmobile, txtemail, txtpass;
    TextView txtlogin;
    ProgressBar progress;
    FirebaseAuth uAuth;
    FirebaseFirestore fstore;
    String userID;
    ImageView profileimage;
    StorageReference storageReference;

    //DatabaseReference reff;
    //User user;
    //long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);

        txtname = (EditText) findViewById(R.id.ureg_fullname);
        txtmobile = (EditText) findViewById(R.id.ureg_mobnum);
        txtemail = (EditText) findViewById(R.id.ureg_email);
        txtpass = (EditText) findViewById(R.id.ureg_pass);
        btnregister = (Button) findViewById(R.id.ureg_registerBtn);
        txtlogin = (TextView) findViewById(R.id.ureg_alreadyreg);
        progress = (ProgressBar) findViewById(R.id.ureg_progress);

        uAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileimage = findViewById(R.id.ureg_img);

        /*user=new User();
        reff= FirebaseDatabase.getInstance().getReference().child("User");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

        if (uAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), userDashboard.class));
            finish();
        }

        //Register Button
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = txtemail.getText().toString().trim();
                final String pass = txtpass.getText().toString().trim();
                final String name = txtname.getText().toString();
                final String phone = txtmobile.getText().toString();
                final String type = "user";

                if (TextUtils.isEmpty(email)) {
                    txtemail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    txtpass.setError("Password is required.");
                    return;
                }
                if (pass.length() < 6) {
                    txtpass.setError("Password must be >= 6 characters.");
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                // Register user to Firebase
                uAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(userregister.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = uAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("mobile", phone);
                            user.put("email", email);
                            user.put("pass", pass);
                            user.put("type", type);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), user_personal_details.class));
                        } else {
                            Toast.makeText(userregister.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });

                /*
                Long mob=Long.parseLong(txtmobile.getText().toString().trim());
                user.setFirstName(txtname.getText().toString().trim());
                user.setMob(mob);
                user.setEmail(txtemail.getText().toString().trim());
                user.setPass(txtpass.getText().toString().trim());
                reff.child(String.valueOf(maxid+1)).setValue(user);
                Toast.makeText(userregister.this, "data inserted successfully",Toast.LENGTH_LONG).show();
                */

            }
        });

        //Login Text Button
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), userlogin.class));
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
                profileimage.setImageURI(imageuri);

                uploadImageToFirebase(imageuri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageuri) {
        //upload image to fire base storage
        StorageReference fileref = storageReference.child("profile.jpg");
        fileref.putFile(imageuri).addOnSuccessListener((new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(userregister.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        })).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(userregister.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
