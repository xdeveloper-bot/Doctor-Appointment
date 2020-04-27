package com.example.doctor_appointment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class user_edit_profile extends AppCompatActivity {
    Button btnchange, btnreset;
    ImageView profileimage;
    TextView txtname, txtmobile, txtaddress, txtemail;
    FirebaseAuth uAuth;
    FirebaseFirestore uStore;
    String userID;
    StorageReference storageReference;
    Button resetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);

        resetpass = findViewById(R.id.uedpro_resetbtn);


        //Reset Password

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetpassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter New Password > Characters long");
                passwordResetDialog.setView(resetpassword);

                passwordResetDialog.setPositiveButton("Yes",onClick();
            }
        });


    }


}