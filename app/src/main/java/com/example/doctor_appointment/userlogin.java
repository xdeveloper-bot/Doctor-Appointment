package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userlogin extends AppCompatActivity {
    EditText txtemail,txtpass;
    Button btnlogin;
    TextView txtregister;
    ProgressBar progress;
    FirebaseAuth uAuth;

    /*
    public void init(){

        btnsignup= (Button)findViewById(R.id.btn_signup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(userlogin.this, userregister.class);
                startActivity(reg);
            }
        });

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        //init();

        txtemail=(EditText)findViewById(R.id.ulin_email);
        txtpass=(EditText)findViewById(R.id.ulin_pass);
        btnlogin=(Button)findViewById(R.id.ulin_loginBtn);
        txtregister=(TextView)findViewById(R.id.ulin_createaccount);
        progress=(ProgressBar)findViewById(R.id.ulin_progressBar);
        uAuth=FirebaseAuth.getInstance();

        if(uAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),userDashboard.class));
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
                uAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(userlogin.this,"Logged in successfully.",Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),user_personal_details.class));
                        }else {
                            Toast.makeText(userlogin.this,"Error!! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        //Create Account Btn
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),userregister.class));
            }
        });

    }
}
