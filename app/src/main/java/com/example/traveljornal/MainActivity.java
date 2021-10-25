package com.example.traveljornal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.DatabaseAccessInterface;
import com.example.traveljornal.databaseclasses.User;

import java.util.List;


public class MainActivity extends AppCompatActivity{
    public static AppDatabase appDatabase;
    private UserListAdapter userListAdapter;
    private EditText username; //private so not accessed from other files
    private EditText password;
    private Button login;
    private Button deleteUser;
    private String TestUser = "Admin";
    private String TestPass = "12345";
    private Button register;
    private Button update;
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

        deleteUser = findViewById(R.id.deleteUserButton);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content,  new DeleteUserFragment())
                        .addToBackStack("fragment_delete_user") // name can be null
                        .commit();
                        username.setVisibility(View.GONE);
                        password.setVisibility(View.GONE);
            }
        });

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
        if (user.equals(TestUser) && pass.equals(TestPass)) {
            return true;
        }
        return false;
    }
}