package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        txtname = findViewById(R.id.ureg_fullname);
        txtmobile = findViewById(R.id.ureg_mobnum);
        txtemail = findViewById(R.id.ureg_email);
        txtpass = findViewById(R.id.ureg_pass);
        btnregister = findViewById(R.id.ureg_registerBtn);
        txtlogin = findViewById(R.id.ureg_alreadyreg);
        progress = findViewById(R.id.ureg_progress);

        uAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

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
                            FirebaseUser usr = uAuth.getCurrentUser();
                            usr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(user_register.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: " + e.getMessage());
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
