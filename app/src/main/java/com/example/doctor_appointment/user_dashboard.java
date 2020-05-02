package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
    Button resendbtn;
    TextView resendbg,resendtxt,txtsearch,txtbookappointment;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        txtsearch=findViewById(R.id.udash_search);
        txtbookappointment=findViewById(R.id.udash_bookappointment);
        drawerLayout = findViewById(R.id.udash_drawer_layout);
        navigationView = findViewById(R.id.udash_nav_view1);
        toolbar = findViewById(R.id.udash_toolbar1);

        fAuth=FirebaseAuth.getInstance();

        resendbg=findViewById(R.id.udash_black);
        resendtxt=findViewById(R.id.udash_notverifytxt);
        resendbtn=findViewById(R.id.udash_resendcode);
        final FirebaseUser usr=fAuth.getCurrentUser();

        if(!usr.isEmailVerified()){
            resendbg.setVisibility(View.VISIBLE);
            resendtxt.setVisibility(View.VISIBLE);
            resendbtn.setVisibility(View.VISIBLE);

            resendbtn.setOnClickListener(new View.OnClickListener() {
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
                            Log.d("TAG","onFailure: "+e.getMessage());
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

        txtbookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bookappointment.class));
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
            case R.id.arrow:
                startActivity(new Intent(getApplicationContext(), user_profile.class));
                break;
            case R.id.nav_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            case R.id.nav_setting:
                startActivity(new Intent(getApplicationContext(), setting.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
