package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class doctor_list extends AppCompatActivity {
    Button btnBook;
    ImageView imgProfile, imgArrow;
    TextView txtName, txtSpecialty, txtHospital;
    EditText txtSearch;
    String valFromBookAppointment;
    Integer intNum = 1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        txtSearch = findViewById(R.id.dlst_searchtxt);
        valFromBookAppointment = getIntent().getExtras().getString("value");
        txtSearch.setText(valFromBookAppointment);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("doctors")
                .whereEqualTo("designation", valFromBookAppointment)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                Log.d("TAG", document.getId() + " => " + document.get("name"));
                                // Create multiple Card Layout


                                intNum += 1;
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ",task.getException());
                        }
                    }
                });

        //Next line here


    }
}
