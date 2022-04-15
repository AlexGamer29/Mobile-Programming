package com.midterm.nguyenhuuminhduc.Api;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm.nguyenhuuminhduc.Model.Data;

import java.util.List;

@Dao
public interface DataDao {

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
