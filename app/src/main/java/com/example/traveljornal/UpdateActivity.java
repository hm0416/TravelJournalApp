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

public class UpdateActivity extends AppCompatActivity {

    private Button submit;
    private EditText userId;
    private EditText password;
    private Button lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        userId = (EditText) findViewById(R.id.userTextFieldForUpdate);
        password = (EditText) findViewById(R.id.passwordTextFieldForUpdate2);
        submit = findViewById(R.id.doneButtonForUpdate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUserID = userId.getText().toString();
                String inputPassword = password.getText().toString();
                if (inputUserID.isEmpty() ||inputPassword.isEmpty()) {
                    Toast.makeText(UpdateActivity.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
                }
                updateUser(inputUserID, inputPassword);

            }
        });

        lang = (Button) findViewById(R.id.langBtnUpdate);
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

    private void updateUser(String userID, String password) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        User user = new User();
        String username =  user.getUsername();
        user.setId(userID);
        user.setPassword(password);
        db.DatabaseAccessInterface().updateData(user);
        finish();
    }
}
