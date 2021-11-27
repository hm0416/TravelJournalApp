package com.example.traveljornal;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.User;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity{
    public EditText username; //private so not accessed from other files
    private EditText password;
    private Button login;
    private Button register;
    private Button update;
    private Button lang;
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

                //used this video for login code https://www.youtube.com/watch?v=LCrhddpsgKU&list=WL&index=2&t=1080s
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
                        intent.putExtra(Intent.EXTRA_TEXT, username.getText().toString());
                        startActivity(intent);
                    }
                }

            }

        });

        register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code to go to the register activity
                startActivityForResult(new Intent(MainActivity.this, RegisterActivity.class), 100);
            }
        });

        update = findViewById(R.id.updateButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code to go to the register activity
                startActivityForResult(new Intent(MainActivity.this, UpdateActivity.class), 100);
            }
        });

        lang = (Button) findViewById(R.id.langBtn); //https://stackoverflow.com/questions/46051697/how-can-i-change-strings-xml-by-clicking-a-button
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("App started", "onStart() method started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("User navigated back", "onResume() method started");
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
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> listOfUsers = db.DatabaseAccessInterface().getAllUsers();
        System.out.println(listOfUsers);
        boolean flag = false;
        for (int i = 0; i < listOfUsers.size(); i++)
        {
                User x = listOfUsers.get(i);
                if(x.username.equals(user)) {
                    if(x.password.equals(pass))
                    {
                        flag = true;
                        break;
                    }
                }
        }
        return flag;

    }
}
