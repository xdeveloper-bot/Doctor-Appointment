package com.example.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class user_search extends AppCompatActivity {
    Toolbar toolbar;
    ListView listview;
    FirebaseFirestore fstore;
    String[] characters={"q","w","e","r","t","y","q","w","e","r","t","y","e","r","t","y","q","w","e","r","t","y","q","w","e","r","t","y"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        toolbar=findViewById(R.id.usea_toolbar);
        listview=findViewById(R.id.usea_listview);
        fstore = FirebaseFirestore.getInstance();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //google
        fstore.collection("doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<String> list = new ArrayList<String>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                list.add(document.getId());
                            }
                            Log.d("TAG",list.toString());
                        } else {
                            Log.d("TAG", "Error getting document: ",task.getException());
                        }
                    }
                });

        //google

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, int i, long l){
                Toast.makeText(getApplicationContext(),"Item Clicked: " + i,Toast.LENGTH_SHORT).show();
            }
        });




    }
}
