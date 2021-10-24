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
        public void addUser(User user);

        @Query("Select * from users")
        public List<User> readUser();

        @Update
        public void updateData(User user);

        @Delete
        public void deleteData(User user);

    }


