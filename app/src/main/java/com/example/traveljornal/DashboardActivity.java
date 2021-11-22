package com.example.traveljornal;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    Button mapBtn;
    Button country;
    TextView search;
    private Button lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mapBtn = (Button) findViewById(R.id.mapButton);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
                String user = getIntent().getStringExtra(Intent.EXTRA_TEXT);
                intent.putExtra(Intent.EXTRA_TEXT, user);
                startActivity(intent);
            }
        });

        lang = (Button) findViewById(R.id.langBtnDash);
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = getBaseContext().getResources().getConfiguration();
                Locale locale = new Locale("es");
                Locale.setDefault(locale);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
            }
        });
    }
}