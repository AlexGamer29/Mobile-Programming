package com.midterm.minhduc;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface DataDao {

    // below line is use to delete a
    // specific course in our database.
    @Delete
    void delete(Data data);

    @Insert
    void insertAll(Data... datas);

    @Insert
    void insert(Data data);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM Data")
    void deleteAll();

    // below line is to read all the courses from our database.
    // in this we are ordering our courses in ascending order
    // with our course name.
    @Query("SELECT * FROM Data ORDER BY title ASC")
    LiveData<List<Data>> getAllDatas();
}
