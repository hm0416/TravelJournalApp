package com.example.traveljornal;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    Button mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mapBtn = (Button) findViewById(R.id.mapButton);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = new MapsFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.map, fr).addToBackStack("fragment_maps").commit();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.map, fr);
//                fragmentTransaction.commit();
            }
        });
    }
}