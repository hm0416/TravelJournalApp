package com.example.traveljornal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.traveljornal.databaseclasses.AppDatabase;
import com.example.traveljornal.databaseclasses.User;
import com.example.traveljornal.databaseclasses.DatabaseAccessInterface;

public class DeleteUserFragment extends Fragment {
    private EditText TxtUserID;
    private Button DeleteButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_delete_user, container, false);
        TxtUserID = view.findViewById(R.id.userIDTextField);
        DeleteButton = view.findViewById(R.id.deleteUserButton);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = AppDatabase.getDbInstance(getActivity().getApplicationContext());
                String id = TxtUserID.getText().toString();

                User user = new User();
                user.setId(id);
                db.DatabaseAccessInterface().deleteData(user);
            }
        });
        return view;
    }

}
