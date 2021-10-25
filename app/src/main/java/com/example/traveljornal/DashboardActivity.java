package com.example.traveljornal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

//    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        update = findViewById(R.id.updateButton);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // code to go to the register activity
//                startActivityForResult(new Intent(DashboardActivity.this, UpdateActivity.class), 100);
////                initRecyclerView();
////                loadUserList();
//            }
//        });
    }
}