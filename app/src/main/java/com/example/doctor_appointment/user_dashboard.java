package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button resendBtn;
    ConstraintLayout verifyEmailLayout;
    TextView txtBookAppointment, txtHealthData, txtDaily, txtAdvice;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        txtBookAppointment = findViewById(R.id.dash_bookAppointment);
        txtHealthData = findViewById(R.id.dash_healthData);
        txtDaily = findViewById(R.id.dash_daily);
        txtAdvice = findViewById(R.id.dash_advice);
        drawerLayout = findViewById(R.id.dash_DrawerLayout);
        navigationView = findViewById(R.id.dash_navView);
        toolbar = findViewById(R.id.dash_toolBar);
        verifyEmailLayout = findViewById(R.id.dash_verifyEmail);
        resendBtn = findViewById(R.id.dash_resendcode);

        fAuth = FirebaseAuth.getInstance();

        final FirebaseUser usr = fAuth.getCurrentUser();

        if (!usr.isEmailVerified()) {
            verifyEmailLayout.setVisibility(View.VISIBLE);

            resendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(user_dashboard.this, "Verification Email has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: " + e.getMessage());
                        }
                    });
                }
            });
        }

        setSupportActionBar(toolbar);

//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(true);
//        menu.findItem(R.id.arrow).setVisible(true);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        txtBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), bookappointment.class));
            }
        });

        txtHealthData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), health_data.class));
            }
        });

        txtDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), daily_routine.class));
            }
        });

        txtAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), free_advice.class));
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                onBackPressed();
                break;
            case R.id.head_arrow1:
                startActivity(new Intent(getApplicationContext(), user_edit_profile.class));
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_search:
                startActivity(new Intent(getApplicationContext(), bookappointment.class));
                break;
            case R.id.nav_logout:
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.nav_appoint:
                startActivity(new Intent(getApplicationContext(), appointments.class));
                break;
            case R.id.nav_test_booking:
                startActivity(new Intent(getApplicationContext(), test_booking.class));
                break;
            case R.id.nav_medical:
                startActivity(new Intent(getApplicationContext(), medical_records.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(getApplicationContext(), setting.class));
                break;
            case R.id.nav_contact:
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
