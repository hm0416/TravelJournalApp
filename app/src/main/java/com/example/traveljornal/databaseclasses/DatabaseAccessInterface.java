package com.example.traveljornal.databaseclasses;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
    @Dao
    public interface DatabaseAccessInterface {
        @Insert
        public void addUser(User...users);

        @Query("Select * from users")
        public List<User> getAllUsers();

        @Update
        public void updateData(User user);

        @Delete
        public void deleteData(User user);

    }


