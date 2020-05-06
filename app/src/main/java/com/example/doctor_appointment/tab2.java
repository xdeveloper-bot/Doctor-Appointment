package com.example.doctor_appointment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {
    //TextView txtSun, txtMon, txtTue, txtWed, txtThu, txtFri, txtSat;

    public tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        TextView Sun = view.findViewById(R.id.t2_sunday);
        TextView Mon = view.findViewById(R.id.t2_monday);
        TextView Tue = view.findViewById(R.id.t2_tuesday);
        TextView Wed = view.findViewById(R.id.t2_wednesday);
        TextView Thu = view.findViewById(R.id.t2_thursday);
        TextView Fri = view.findViewById(R.id.t2_friday);
        TextView Sat = view.findViewById(R.id.t2_saturday);

        Sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Sunday");
                startActivity(i);
            }
        });

        Mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Monday");
                startActivity(i);
            }
        });

        Tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Tuesday");
                startActivity(i);
            }
        });

        Wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Wednesday");
                startActivity(i);
            }
        });

        Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Thursday");
                startActivity(i);
            }
        });

        Fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Friday");
                startActivity(i);
            }
        });

        Sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), show_routine.class);
                i.putExtra("day", "Saturday");
                startActivity(i);
            }
        });

        return view;
    }
}
