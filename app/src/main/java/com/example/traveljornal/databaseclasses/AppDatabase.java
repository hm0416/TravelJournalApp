package com.example.traveljornal.databaseclasses;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;
 @Dao
public abstract class AppDatabase  extends RoomDatabase{
    public abstract AppDatabase appDatabaseObject();
}
