package com.midterm.minhduc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DataViewModel extends ViewModel {
    private LiveData<List<Data>> users;

    public LiveData<List<Data>> getUsers() {
        loadUsers();
        return users;
    }

    private void loadUsers() {
        DataApiService dataApiService = DataApiService.getInstance();
        users = (LiveData<List<Data>>) dataApiService.getDatas();
    }
}
