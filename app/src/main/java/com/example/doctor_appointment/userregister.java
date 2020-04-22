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

public class userregister extends AppCompatActivity {
    Button btnregister;
    EditText txtfname,txtlname,txtmobile,txtemail,txtdob,txtpass;
    DatabaseReference reff;
    User user;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);

        txtfname=(EditText)findViewById(R.id.first_name);
        txtlname=(EditText)findViewById(R.id.last_name);
        txtmobile=(EditText)findViewById(R.id.mob_num);
        txtemail=(EditText)findViewById(R.id.user_email);
        txtdob=(EditText)findViewById(R.id.user_dob);
        txtpass=(EditText)findViewById(R.id.user_pass);
        btnregister=(Button)findViewById(R.id.submit_btn);

        user=new User();
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

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long mob=Long.parseLong(txtmobile.getText().toString().trim());
                user.setFirstName(txtfname.getText().toString().trim());
                user.setLastName(txtlname.getText().toString().trim());
                user.setMob(mob);
                user.setEmail(txtemail.getText().toString().trim());
                user.setDOB(txtdob.getText().toString().trim());
                user.setPass(txtpass.getText().toString().trim());
                reff.child(String.valueOf(maxid+1)).setValue(user);
                Toast.makeText(userregister.this, "data inserted successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
