package com.example.traveljornal;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import androidx.fragment.app.Fragment;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.User;
import androidx.room.Query;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.DatabaseAccessInterface;
import com.example.traveljornal.databaseclasses.User;

import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity{
    public static AppDatabase appDatabase;
    private UserListAdapter userListAdapter;
    public EditText username; //private so not accessed from other files
    private EditText password;
    private Button login;
    private Button deleteUser;
    private String TestUser = "Admin";
    private String TestPass = "12345";
    private Button register;
    private Button update;
    private Button lang;
    FragmentManager fm;
    boolean isAccValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //appDatabase = Room.databaseBuilder(this,AppDatabase.class,"userinfo").allowMainThreadQueries().build();

        Log.d("Login page", "OnCreate() method launched");

        register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code to go to the register activity
                startActivityForResult(new Intent(MainActivity.this, RegisterActivity.class), 100);
                initRecyclerView();
                loadUserList();
            }
        });
        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passTextField);

//        deleteUser = findViewById(R.id.deleteUserButton);
//        deleteUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//
//                fragmentManager.beginTransaction()
//                        .replace(R.id.content,  new DeleteUserFragment())
//                        .addToBackStack("fragment_delete_user") // name can be null
//                        .commit();
//                        username.setVisibility(View.GONE);
//                        password.setVisibility(View.GONE);
//            }
//        });

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
                initRecyclerView();
                loadUserList();
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

        lang = (Button) findViewById(R.id.langBtn);
        lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration config = getBaseContext().getResources().getConfiguration();
                Locale locale = new Locale("es");
                Locale.setDefault(locale);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                recreate();
//                Resources res = getResources();
//                DisplayMetrics dm = res.getDisplayMetrics();
//                Configuration conf = res.getConfiguration();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                    conf.setLocale(new Locale("es"));
//                } else {
//                    conf.locale = new Locale("es");
//                }
//                res.updateConfiguration(conf, dm);
//                Locale locale = new Locale("es");
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
//                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

//                Button but = (Button) v;
//                Resources res = getResources();
//                String current = res.getConfiguration().locale.getCountry();
//                Log.i("Current", current);
//                String localeString = new String(current);
//                if (but.equals(lang)) {
//                    localeString = "ES";
//                }
//                Log.i("Clicked", localeString);
//
//                if (!current.equalsIgnoreCase(localeString) && localeString.length() > 0) {
//                    // Change locale settings in the app.
//                    DisplayMetrics dm = res.getDisplayMetrics();
//                    android.content.res.Configuration conf = res.getConfiguration();
//                    conf.locale = new Locale(localeString.toLowerCase());
//                    res.updateConfiguration(conf, dm);
////                    //refresh menu
////                    setGridView()
////                    //added this line to get refreshed listener
////                    changeLanguageListener();
//                }
            }
        });

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(this);
        recyclerView.setAdapter(userListAdapter);
    }

    private void loadUserList(){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> userList = db.DatabaseAccessInterface().getAllUsers();
        userListAdapter.setUserList(userList);
    }

    public String getCurrentUser() {
        return username.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadUserList();
        }
        super.onActivityResult(requestCode, resultCode, data);
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