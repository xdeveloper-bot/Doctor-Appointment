package com.example.doctor_appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userregister extends AppCompatActivity {
    Button btnregister;
    EditText txtfname,txtlname,txtmobile,txtemail,txtdob,txtpass;
    DatabaseReference reff;
    User user;

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
                reff.push().setValue(user);
                //Toast.makeText(datainsert.this, "data inserted successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
