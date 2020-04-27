package com.example.doctor_appointment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class user_details extends AppCompatActivity {
    EditText txtname,txtemail,txtdob,txtaddress,txtstate,txtzip;
    DatePicker txtdatepicker;
    Button btnsubmit;
    ProgressBar progress;
    FirebaseAuth uAuth;
    FirebaseFirestore uStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        txtname=findViewById(R.id.udtl_name);
        txtemail=findViewById(R.id.udtl_email);
        txtdob=findViewById(R.id.udtl_dob);
        txtaddress=findViewById(R.id.udtl_address);
        txtstate=findViewById(R.id.udtl_state);
        txtzip=findViewById(R.id.udtl_zip);
        txtdatepicker=findViewById(R.id.udtl_datepicker);
        progress=findViewById(R.id.udtl_progressBar);
        btnsubmit=findViewById(R.id.udtl_submitBtn);

        uAuth=FirebaseAuth.getInstance();
        uStore=FirebaseFirestore.getInstance();

        userID = uAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = uStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
                txtemail.setText(documentSnapshot.getString("email"));
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

                Map<String,Object> user = new HashMap<>();
                user.put("name", name);
                user.put("email",email);
                user.put("dob",dob);
                user.put("address",address);
                user.put("state",state);
                user.put("zip",zip);
                documentReference.set(user);

                Toast.makeText(user_details.this,"Profile Created.",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                startActivity(new Intent(getApplicationContext(), user_dashboard.class));

            }
        });

    }
}
