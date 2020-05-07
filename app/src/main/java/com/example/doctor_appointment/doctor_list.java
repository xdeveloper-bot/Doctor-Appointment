package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class doctor_list extends AppCompatActivity {
    EditText txtSearch;
    FirebaseAuth fAuth;
    String valFromActivity;
    FirebaseFirestore fStore;
    Toolbar toolbar;
    String searchText;
    Integer intNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        toolbar = findViewById(R.id.dlst_toolbar);
        txtSearch = findViewById(R.id.dlst_searchtxt);
        valFromActivity = getIntent().getExtras().getString("value");
        txtSearch.setText(valFromActivity);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                searchText = String.valueOf(s);
                Toast.makeText(getApplicationContext(), searchText, Toast.LENGTH_SHORT).show();
            }
        };

        txtSearch.addTextChangedListener(textWatcher);

        fStore.collection("doctors")
                .whereEqualTo("designation", valFromActivity)
                //.where()
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
                            LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("TAG", document.getId() + " ==> " + document.getData());
                                String Name = document.get("name").toString().trim();
                                String Hospital = document.get("hospital").toString().trim();
                                String Designation = document.get("designation").toString().trim();

                                // Create multiple Card Layout
                                View tempView = li.inflate(R.layout.doctor_list_templete, null);

                                TextView txtName = (TextView) tempView.findViewById(R.id.tmp_name);
                                TextView txtSpecialty = (TextView) tempView.findViewById(R.id.tmp_specialty);
                                TextView txtHospital = (TextView) tempView.findViewById(R.id.tmp_hospital);
                                ImageView imgProfile = (ImageView) tempView.findViewById(R.id.tmp_profileimg);
                                ImageView imgArrow = (ImageView) tempView.findViewById(R.id.tmp_arrow);
                                Button btnBook = (Button) tempView.findViewById(R.id.tmp_btn);

                                txtName.setText(Name);
                                txtSpecialty.setText(Designation);
                                txtHospital.setText(Hospital);
                                imgProfile.setImageResource(R.drawable.doctor);
                                imgArrow.setImageResource(R.drawable.ic_chevron);
                                btnBook.setOnClickListener(btnClick);
                                btnBook.setId(intNum);
                                intNum++;
                                mainLayout.addView(tempView);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ",task.getException());
                        }
                    }
                });

        //Next line here

    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*switch (v.getId()){
                case 0:
                    // action
                    break;
                case 1:
                    // as
                    break;

                default:
                    break;
            }*/
            Toast.makeText(getApplicationContext(), "Button" + v.getId(), Toast.LENGTH_SHORT).show();
        }
    };

}
