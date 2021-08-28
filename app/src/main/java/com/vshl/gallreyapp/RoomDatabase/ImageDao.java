package com.vshl.gallreyapp.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.vshl.gallreyapp.RoomDatabase.models.ListDataModel;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM listdatamodel")
    List<ListDataModel> getAll();


    @Insert
    void insert(ListDataModel listdatamodel);

    @Delete
    void delete(ListDataModel listdatamodel);

    @Update
    void update(ListDataModel listdatamodel);

    @Query("DELETE FROM listdatamodel")
    public void nukeTable();

    @Transaction
    public default void replaceUsers(List<ListDataModel> listdatamodel) {
        nukeTable();
        insertAllUsers(listdatamodel);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllUsers(List<ListDataModel> listdatamodel);

}
