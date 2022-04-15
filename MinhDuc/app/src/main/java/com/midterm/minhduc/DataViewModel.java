package com.midterm.minhduc;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.midterm.minhduc.repo.Repo;

import java.util.List;

import kotlinx.coroutines.flow.Flow;
import retrofit2.Call;

public class DataViewModel extends AndroidViewModel {
    private Repo repository;
    private LiveData<List<Data>> allDatas;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = new Repo(application);
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
