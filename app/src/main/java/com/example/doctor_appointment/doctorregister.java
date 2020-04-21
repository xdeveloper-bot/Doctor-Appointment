package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class doctorregister extends AppCompatActivity {
    Button btnregister;
    EditText txtfname,txtlname,txtmobile,txtemail,txtdob,txtpass;
    DatabaseReference reff;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorregister);

        txtfname=(EditText)findViewById(R.id.first_name);
        txtlname=(EditText)findViewById(R.id.last_name);
        txtmobile=(EditText)findViewById(R.id.mob_num);
        txtemail=(EditText)findViewById(R.id.user_email);
        txtdob=(EditText)findViewById(R.id.user_dob);
        txtpass=(EditText)findViewById(R.id.user_pass);
        btnregister=(Button)findViewById(R.id.submit_btn);

        doctor=new Doctor();
        reff= FirebaseDatabase.getInstance().getReference().child("User");

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long mob=Long.parseLong(txtmobile.getText().toString().trim());
                doctor.setFirstName(txtfname.getText().toString().trim());
                doctor.setLastName(txtlname.getText().toString().trim());
                doctor.setMob(mob);
                doctor.setEmail(txtemail.getText().toString().trim());
                doctor.setDOB(txtdob.getText().toString().trim());
                doctor.setPass(txtpass.getText().toString().trim());
                reff.push().setValue(doctor);
                //Toast.makeText(datainsert.this, "data inserted successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}


