package com.example.traveljornal;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.User;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private Button submit;
    private EditText fullName;
    private EditText userId;
    private EditText email;
    private EditText password;
    private EditText username;
    private Button lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        fullName = (EditText) findViewById(R.id.fullNameTextField);
        userId = (EditText) findViewById(R.id.userIDTextField);
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        submit = findViewById(R.id.doneButton);
        username = findViewById(R.id.usernameTextField);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUserID = userId.getText().toString();
                String inputFullName = fullName.getText().toString();
                String inputEmail = email.getText().toString();
                String inputPassword = password.getText().toString();
                String inputUserName = username.getText().toString();
                if (inputUserID.isEmpty() || inputFullName.isEmpty() || inputEmail.isEmpty() ||inputPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
                saveNewUser(inputUserID, inputFullName, inputEmail, inputPassword, inputUserName);


            }
        });

        lang = (Button) findViewById(R.id.langBtnReg);
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

    private void saveNewUser(String userID, String fullName, String email, String password, String username) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        User user =  new User();
        user.FullName = fullName;
        user.username = username;
        user.email = email;
        user.password = password;
        user.id = userID;
        db.DatabaseAccessInterface().addUser(user);
        finish();
    }
}
