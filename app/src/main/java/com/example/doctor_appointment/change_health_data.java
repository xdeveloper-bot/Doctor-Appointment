package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class change_health_data extends AppCompatActivity {
    EditText txtBp, txtSugar, txtDiabetes, txtOther;
    Button btnSave;
    Toolbar toolbar;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_health_data);

        toolbar = findViewById(R.id.chdta_toolbar);
        btnSave = findViewById(R.id.chdta_saveBtn);
        txtBp = findViewById(R.id.chdta_bp);
        txtSugar = findViewById(R.id.chdta_sugar);
        txtDiabetes = findViewById(R.id.chdta_diabetes);
        txtOther = findViewById(R.id.chdta_other);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Bp = txtBp.getText().toString().trim();
                String Sugar = txtSugar.getText().toString().trim();
                String Diabetes = txtDiabetes.getText().toString().trim();
                String Other = txtOther.getText().toString().trim();

                if (TextUtils.isEmpty(Bp)) {
                    txtBp.setError("Blood Pressure is Required.");
                    return;
                }
                if (TextUtils.isEmpty(Sugar)) {
                    txtSugar.setError("Sugar is Required.");
                    return;
                }
                if (TextUtils.isEmpty(Diabetes)) {
                    txtDiabetes.setError("Diabetes is Required.");
                    return;
                }

                Map<String, Object> nested = new HashMap<>();
                nested.put("bp", Bp);
                nested.put("sugar", Sugar);
                nested.put("diabetes", Diabetes);
                nested.put("other", Other);

                Map<String, Object> hData = new HashMap<>();
                hData.put("health_data", nested);
                fStore.collection("users").document(userID).update(hData);

                Intent i = new Intent(getApplicationContext(), health_data.class);
                i.putExtra("bp", Bp);
                i.putExtra("sugar", Sugar);
                i.putExtra("diabetes", Diabetes);
                i.putExtra("other", Other);
                startActivity(i);
                finish();
            }
        });
    }
}
