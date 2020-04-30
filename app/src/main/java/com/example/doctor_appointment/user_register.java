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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class user_register extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button btnregister;
    EditText txtname, txtmobile, txtemail, txtpass;
    TextView txtlogin;
    ProgressBar progress;
    FirebaseAuth uAuth;
    FirebaseFirestore fstore;
    String userID;

    //DatabaseReference reff;
    //User user;
    //long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        txtname = (EditText) findViewById(R.id.ureg_fullname);
        txtmobile = (EditText) findViewById(R.id.ureg_mobnum);
        txtemail = (EditText) findViewById(R.id.ureg_email);
        txtpass = (EditText) findViewById(R.id.ureg_pass);
        btnregister = (Button) findViewById(R.id.ureg_registerBtn);
        txtlogin = (TextView) findViewById(R.id.ureg_alreadyreg);
        progress = (ProgressBar) findViewById(R.id.ureg_progress);

        uAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

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

                            //Verify Email
                            FirebaseUser usr=uAuth.getCurrentUser();
                            usr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(user_register.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure: "+e.getMessage());
                                }
                            });

                            Toast.makeText(user_register.this, "User Created.", Toast.LENGTH_SHORT).show();
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

                            startActivity(new Intent(getApplicationContext(), user_details.class));
                        } else {
                            Toast.makeText(user_register.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(), user_login.class));
            }
        });

    }

}
