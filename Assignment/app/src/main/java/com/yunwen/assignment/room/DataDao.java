package com.yunwen.assignment.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * from data_table ORDER BY userId ASC")
    LiveData<List<Data>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Data dataModel);

    @Query("DELETE FROM data_table")
    void deleteAll();
}
