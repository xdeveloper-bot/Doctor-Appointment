package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class doctor_login extends AppCompatActivity {
    EditText txtemail,txtpass;
    Button btnlogin;
    TextView txtregister,  forgetpass;
    ProgressBar progress;
    FirebaseAuth dAuth;

    /*public void init(){

        btnsignup= (Button)findViewById(R.id.dl);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(doctorlogin.this, doctorregister.class);
                startActivity(reg);
            }
        });

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        //init();

        txtemail=(EditText)findViewById(R.id.dlin_email);
        txtpass=(EditText)findViewById(R.id.dlin_pass);
        btnlogin=(Button)findViewById(R.id.dlin_loginBtn);
        txtregister=(TextView)findViewById(R.id.dlin_createaccount);
        progress=(ProgressBar)findViewById(R.id.dlin_progressBar);
        dAuth=FirebaseAuth.getInstance();
        forgetpass = findViewById(R.id.dlin_forget);

        if(dAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), doctor_dashboard.class));
            finish();
        }

        //Login Button
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtemail.getText().toString().trim();
                String pass = txtpass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    txtemail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    txtpass.setError("Password is required.");
                    return;
                }
                if(pass.length() < 6){
                    txtpass.setError("Password must be >= 6 characters.");
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                // Authenticate user
                dAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(doctor_login.this,"Logged in successfully.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), doctor_dashboard.class));
                            finish();
                        }else {
                            Toast.makeText(doctor_login.this,"Error!! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
                        dAuth.sendPasswordResetEmail(mail).addOnSuccessListener((new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(doctor_login.this, "Sent To mail", Toast.LENGTH_SHORT).show();

                            }
                        })).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(doctor_login.this, "Error Reset Link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(), doctor_register.class));
                finish();
            }
        });

    }
}
