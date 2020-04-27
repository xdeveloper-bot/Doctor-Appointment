package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class doctor_edit_profile extends AppCompatActivity {
    ImageView profileimg;
    Button btnchange,btnreset;
    TextView txtname,txtemail,txtmobile,txtaddress,txtspecialty;
    FirebaseAuth dAuth;
    FirebaseFirestore fstore;
    StorageReference storageReference;
    String docID;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);

        profileimg=findViewById(R.id.dedpro_img);
        btnchange=findViewById(R.id.dedpro_changebtn);
        btnreset=findViewById(R.id.dedpro_resetbtn);
        txtname=findViewById(R.id.dedpro_name);
        txtemail=findViewById(R.id.dedpro_email);
        txtmobile=findViewById(R.id.dedpro_mobile);
        txtaddress=findViewById(R.id.dedpro_address);
        txtspecialty=findViewById(R.id.dedpro_specialty);

        dAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        docID=dAuth.getCurrentUser().getUid();
        user= dAuth.getCurrentUser();

        DocumentReference documentReference=fstore.collection("doctors").document(docID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtname.setText(documentSnapshot.getString("name"));
                txtemail.setText(documentSnapshot.getString("email"));
                txtmobile.setText(documentSnapshot.getString("mobile"));
                txtaddress.setText(documentSnapshot.getString("address"));
                txtspecialty.setText(documentSnapshot.getString("specialty"));
            }
        });

        StorageReference profileRef = storageReference.child("users/"+ docID +"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimg);
            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),doctor_details.class));

            }
        });


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reset Password code here      <--
                final Button resetpassword = new Button(v.getContext());
                AlertDialog.Builder passwordresretdialog = new AlertDialog.Builder(v.getContext());
                passwordresretdialog.setTitle("Reset password");
                passwordresretdialog.setMessage("Enter your to Recived reset link");
                passwordresretdialog.setView(resetpassword);

                passwordresretdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPassword = resetpassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(doctor_edit_profile.this, "Password Reser Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(doctor_edit_profile.this, "Password Reset Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordresretdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Close Dialog
                    }
                });
                passwordresretdialog.create().show();
            }

        });


    }
}
