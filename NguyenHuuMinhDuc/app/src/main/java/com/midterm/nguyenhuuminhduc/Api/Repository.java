package com.midterm.nguyenhuuminhduc.Api;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.midterm.nguyenhuuminhduc.Model.Data;

import java.util.List;

public class Repository {

    private DataDao dataDao;
    private LiveData<List<Data>> allDatas;

    public Repository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        dataDao = database.dataDao();
        allDatas = dataDao.getAllDatas();
    }

    public void insert(Data data) {
        new InsertNoteAsyncTask(dataDao).execute(data);
    }

//    public void insert(Data data) {
//        new InsertNoteAsyncTask(dataDao).execute(data);
//    }

    public void delete(Data data) {
        new DeleteNoteAsyncTask(dataDao).execute(data);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(dataDao).execute();
    }

    public LiveData<List<Data>> getAllDatas() {
        return allDatas;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Data, Void, Void> {
        private DataDao dataDao;

        private InsertNoteAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Data... contacts) {
            dataDao.insert(contacts[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Data, Void, Void> {
        private DataDao dataDao;

        private DeleteNoteAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Data... datas) {
            dataDao.delete(datas[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataDao dataDao;

        private DeleteAllNotesAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAll();
            return null;
        }
    }
}
