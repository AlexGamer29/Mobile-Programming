package com.example.contactapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

//    @Query("SELECT * FROM Contact")
//    public List<Contact> getAllContact();

    @Insert
    public void insertAll(Contact... contacts);

//    @Query("SELECT * FROM Contact WHERE name LIKE :searchText " +
//            "OR mobile LIKE :searchText")
//    public List<Contact> searchContacts(String searchText);

    @Insert
    public void insert(Contact contact);

    @Update
    public void update(Contact contacts);

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();
}
