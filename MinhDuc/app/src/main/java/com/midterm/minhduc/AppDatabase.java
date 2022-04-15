package com.midterm.minhduc;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Database(entities = {Data.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    // below line is to create instance
    // for our database class.
    private static AppDatabase instance;

    // below line is to create
    // abstract variable for dao.
    public abstract DataDao dataDAO();

    // on below line we are getting instance for our database.
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance =
                    // our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "course_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataDao dataDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            dataDao = db.dataDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
