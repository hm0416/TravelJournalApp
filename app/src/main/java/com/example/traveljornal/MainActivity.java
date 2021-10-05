package com.example.traveljornal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username; //private so not accessed from other files
    private EditText password;
    private Button login;
    private String TestUser = "Admin";
    private String TestPass = "12345";
    boolean isAccValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Login page", "OnCreate() method launched");

        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passTextField);
        login = findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get username and pass entered by user
                String inputUsername = username.getText().toString();
                String inputPass = password.getText().toString();

                if (inputUsername.isEmpty() || inputPass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a username and password.", Toast.LENGTH_SHORT).show();
                } else {
                    isAccValid = isAccountValid(inputUsername, inputPass);
                    if (!isAccValid) {
                        Toast.makeText(MainActivity.this, "Please enter a valid username and password.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                        //code to go to dashboard
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("App started", "onStart() method started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("User navigated back.", "onResume() method started");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("User navigated away.", "onPause() method started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity not visible.", "onStop() method started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("App process killed.", "onDestroy() method started");
    }

    private boolean isAccountValid(String user, String pass) {
        if (user.equals(TestUser) && pass.equals(TestPass)) {
            return true;
        }

        return false;
    }
}