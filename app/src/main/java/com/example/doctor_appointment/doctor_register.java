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

public class doctor_register extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button btnregister;
    EditText txtname,txtmobile,txtemail,txtpass;
    TextView txtlogin;
    ProgressBar progress;
    FirebaseAuth dAuth;
    FirebaseFirestore fstore;
    String docID;

    //DatabaseReference reff;
    //Doctor doctor;
    //long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        txtname=(EditText)findViewById(R.id.dreg_fullname);
        txtmobile=(EditText)findViewById(R.id.dreg_mobnum);
        txtemail=(EditText)findViewById(R.id.dreg_email);
        txtpass=(EditText)findViewById(R.id.dreg_pass);
        btnregister=(Button)findViewById(R.id.dreg_registerBtn);
        txtlogin=(TextView)findViewById(R.id.dreg_alreadyreg);
        progress=(ProgressBar)findViewById(R.id.dreg_progress);

        dAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        /*
        txtfname=(EditText)findViewById(R.id.Doctor_Firstname);
        txtlname=(EditText)findViewById(R.id.Doctor_lastname);
        txtmobile=(EditText)findViewById(R.id.Doctor_number);
        txtemail=(EditText)findViewById(R.id.Doctor_emailid);
        txtspeciality=(EditText)findViewById(R.id.Doctor_speciatlity);
        txtpass=(EditText)findViewById(R.id.doc_pass);
        btnregister=(Button)findViewById(R.id.Doctor_register);

        doctor=new Doctor();
        reff= FirebaseDatabase.getInstance().getReference().child("Doctor");
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

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long mob=Long.parseLong(txtmobile.getText().toString().trim());
                doctor.setFirstName(txtfname.getText().toString().trim());
                doctor.setLastName(txtlname.getText().toString().trim());
                doctor.setMob(mob);
                doctor.setEmail(txtemail.getText().toString().trim());
                doctor.setSpeciality(txtspeciality.getText().toString().trim());
                doctor.setPass(txtpass.getText().toString().trim());
                reff.child(String.valueOf(maxid+1)).setValue(doctor);
                Toast.makeText(doctorregister.this, "data inserted successfully",Toast.LENGTH_LONG).show();
            }
        });*/

        if(dAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), doctor_dashboard.class));
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
                final String type = "doctor";

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
                dAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Verify Email
                            FirebaseUser usr=dAuth.getCurrentUser();
                            usr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(doctor_register.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","onFailure: "+e.getMessage());
                                }
                            });

                            Toast.makeText(doctor_register.this, "Doctor Profile Created.", Toast.LENGTH_SHORT).show();
                            docID = dAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("doctors").document(docID);
                            Map<String,Object> doc = new HashMap<>();
                            doc.put("name",name);
                            doc.put("mobile",phone);
                            doc.put("email",email);
                            doc.put("pass",pass);
                            doc.put("type",type);
                            documentReference.set(doc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+docID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), doctor_details.class));
                        } else {
                            Toast.makeText(doctor_register.this, "Error!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(), doctor_login.class));
            }
        });

    }

}
