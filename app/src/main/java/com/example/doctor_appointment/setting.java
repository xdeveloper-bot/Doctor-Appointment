package com.example.doctor_appointment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class setting extends AppCompatActivity {

    TextView textView, textView1;
    FirebaseAuth uAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        textView=findViewById(R.id.snooze);
        textView1=findViewById(R.id.notification);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog1();
            }
        });

    }

    private void showAlertDialog1() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(setting.this);
        alertdialog.setTitle("Snooze Duration");
        String[] items = {"No popup Notification","Only When screen is 'ON'","Only When screen is 'OFF'","Always Show popup"};
        int checkedItem = 1;
        alertdialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Toast.makeText(setting.this,"No popup Notification",Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Toast.makeText(setting.this,"Only When screen is 'ON'",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(setting.this,"Only When screen is 'OFF'",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(setting.this,"Always Show popup",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        AlertDialog alert1 = alertdialog.create();
        alert1.setCanceledOnTouchOutside(false);
        alert1.show();

    }

    private void showAlertDialog() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(setting.this);
        alertdialog.setTitle("Snooze Duration");
        String[] items = {"snooze after 5 second","Snooze after 10 second","Snooze after 15 second"};
        int checkedItem = 1;
        alertdialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Toast.makeText(setting.this,"Snooze after 5 second",Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Toast.makeText(setting.this,"Snooze after 10 second",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(setting.this,"Snooze after 15 second",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();




    }


}
