package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class doctor_list extends AppCompatActivity {
    EditText txtSearch;
    String valFromBookAppointment;
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
                            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
                            LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                // Create multiple Card Layout
                                View tempView = li.inflate(R.layout.doctor_list_templete, null);

                                TextView txtName = (TextView) tempView.findViewById(R.id.tmp_name);
                                TextView txtSpecialty = (TextView) tempView.findViewById(R.id.tmp_specialty);
                                TextView txtHospital = (TextView) tempView.findViewById(R.id.tmp_hospital);
                                ImageView imgProfile = (ImageView) tempView.findViewById(R.id.tmp_profileimg);
                                ImageView imgArrow = (ImageView) tempView.findViewById(R.id.tmp_arrow);
                                Button btnBook = (Button) tempView.findViewById(R.id.tmp_btn);

                                txtName.setText(document.get("name").toString());
                                txtSpecialty.setText(document.get("designation").toString());
                                txtHospital.setText(document.get("hospital").toString());
                                //btnBook.setId();

                                mainLayout.addView(tempView);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ",task.getException());
                        }
                    }
                });

        //Next line here


    }
}
