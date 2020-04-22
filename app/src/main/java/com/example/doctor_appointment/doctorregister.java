package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class doctorregister extends AppCompatActivity {
    Button btnregister;
    EditText txtfname,txtlname,txtmobile,txtemail,txtspeciality,txtpass;
    DatabaseReference reff;
    Doctor doctor;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorregister);

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
                reff.push().setValue(doctor);
                Toast.makeText(doctorregister.this, "data inserted successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
