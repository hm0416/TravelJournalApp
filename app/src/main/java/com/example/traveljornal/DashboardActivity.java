package com.example.traveljornal;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DashboardActivity extends AppCompatActivity {

    Button mapBtn;
    Button country;
    TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        search = (TextView) findViewById(R.id.searchTxt);
//        country = (Button) findViewById(R.id.countryBtn) ;
//        country.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fr = new CountryFragment();
//                FragmentManager fm = getFragmentManager();
//                fm.beginTransaction().replace(R.id.container, fr).addToBackStack("fragment_country").commit();
//                country.setVisibility(View.GONE);
//                search.setVisibility(View.GONE);
//
//            }
//        });

        mapBtn = (Button) findViewById(R.id.mapButton);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
                String user = getIntent().getStringExtra(Intent.EXTRA_TEXT);
                intent.putExtra(Intent.EXTRA_TEXT, user);
                startActivity(intent);
//                Fragment fr = new MapsFragment();
//                FragmentManager fm = getFragmentManager();
//                fm.beginTransaction().replace(R.id.map, fr).addToBackStack("fragment_maps").commit();
////                FragmentTransaction fragmentTransaction = fm.beginTransaction();
////                fragmentTransaction.replace(R.id.map, fr);
////                fragmentTransaction.commit();
            }
        });
    }
}