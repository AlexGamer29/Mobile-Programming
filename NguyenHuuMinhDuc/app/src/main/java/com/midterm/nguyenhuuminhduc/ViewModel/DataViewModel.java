package com.midterm.nguyenhuuminhduc.ViewModel;

import android.app.Application;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.midterm.nguyenhuuminhduc.Api.Repository;
import com.midterm.nguyenhuuminhduc.Model.Data;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Data>> allDatas;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allDatas = repository.getAllDatas();
    }

    public void delete(Data data) {
        repository.delete(data);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Data>> getAllDatas() {
        return allDatas;
    }
}
