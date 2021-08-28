package com.vshl.gallreyapp.RoomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vshl.gallreyapp.RoomDatabase.models.ListDataModel;


@Database(entities = {ListDataModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();
}
