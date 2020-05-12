package com.example.doctor_appointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class user_login extends AppCompatActivity {
    EditText txtemail, txtpass;
    Button btnlogin;
    TextView txtregister, forgetpass;
    ProgressBar progress;
    FirebaseAuth uAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        txtemail = findViewById(R.id.ulin_email);
        txtpass = findViewById(R.id.ulin_pass);
        btnlogin = findViewById(R.id.ulin_loginBtn);
        txtregister = findViewById(R.id.ulin_createaccount);
        progress = findViewById(R.id.ulin_progressBar);
        uAuth = FirebaseAuth.getInstance();
        forgetpass = findViewById(R.id.ulin_forget);

        if (uAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), user_dashboard.class));
            finish();
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString().trim();
                String pass = txtpass.getText().toString().trim();

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

                // Authenticate user
                uAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(user_login.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), user_dashboard.class));
                            finish();
                        } else {
                            Toast.makeText(user_login.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        //forget password
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resentMail = new EditText(v.getContext());
                AlertDialog.Builder passwordresretdialog = new AlertDialog.Builder(v.getContext());
                passwordresretdialog.setTitle("Reset password");
                passwordresretdialog.setMessage("Enter your to Recived reset link");
                passwordresretdialog.setView(resentMail);

                passwordresretdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resentMail.getText().toString();
                        uAuth.sendPasswordResetEmail(mail).addOnSuccessListener((new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(user_login.this, "Sent To mail", Toast.LENGTH_SHORT).show();

                            }
                        })).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(user_login.this, "Error Reset Link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordresretdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close dialog
                    }
                });
                passwordresretdialog.create().show();
            }
        });

        //Create Account Btn
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), user_register.class));
                finish();
            }
        });
    }
}
