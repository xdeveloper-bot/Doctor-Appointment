package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button resendBtn;
    ImageView profileArrow;
    ConstraintLayout verifyEmailLayout;
    FirebaseAuth fAuth;
    FirebaseUser usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.activity_user_dashboard);

        drawerLayout = findViewById(R.id.dash_DrawerLayout);
        navigationView = findViewById(R.id.dash_navView);
        toolbar = findViewById(R.id.dash_toolBar);
        verifyEmailLayout = findViewById(R.id.dash_verifyEmail);
        resendBtn = findViewById(R.id.dash_resendcode);
        View headerView = navigationView.getHeaderView(0);
        profileArrow = headerView.findViewById(R.id.hdr_arrow);

        fAuth = FirebaseAuth.getInstance();
        usr = fAuth.getCurrentUser();

        if (!usr.isEmailVerified()) {
            verifyEmailLayout.setVisibility(View.VISIBLE);
            resendBtn.setOnClickListener(this);
        }

        setSupportActionBar(toolbar);

        //Menu ic_menu = navigationView.getMenu();
        //ic_menu.findItem(R.id.nav_logout).setVisible(true);
        //ic_menu.findItem(R.id.arrow).setVisible(true);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        profileArrow.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dash_resendcode:
                usr.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(user_dashboard.this, "Verification Email has been Sent.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.dash_appointments:
                startActivity(new Intent(this, appointments.class));
                break;
            case R.id.dash_healthData:
                startActivity(new Intent(this, health_data.class));
                break;
            case R.id.dash_daily:
                startActivity(new Intent(this, daily_routine.class));
                break;
            case R.id.dash_advice:
                startActivity(new Intent(this, near_hospital.class));
                break;
            case R.id.hdr_arrow:
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getApplicationContext(), user_profile.class));
                break;
        }
        //overridePendingTransition(R.anim.our_slide_in_left, R.anim.our_slide_out_right);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //overridePendingTransition(R.anim.our_slide_in_right, R.anim.our_slide_out_left);
        }
    }
}
