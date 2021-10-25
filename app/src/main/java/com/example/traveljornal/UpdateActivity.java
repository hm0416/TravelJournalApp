package com.example.traveljornal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.User;

public class UpdateActivity extends AppCompatActivity {

    private Button submit;
    private EditText userId;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        userId = (EditText) findViewById(R.id.userTextFieldForUpdate);
        password = (EditText) findViewById(R.id.passwordTextFieldForUpdate2);
        submit = findViewById(R.id.doneButton);

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
