package com.midterm.minhduc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.midterm.minhduc.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public List<Data> listData;
    public DataAdapter dataAdapter;

    private SearchView searchView;
    private DataApiService dataApiService;
    private DataApi dataApi;

    private AppDatabase appDatabase;
    private DataDao dataDao;

    public DataViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvData.setLayoutManager(new GridLayoutManager(this, 1));
        listData = new ArrayList<>();

        appDatabase = AppDatabase.getInstance(this);
        dataDao = appDatabase.dataDAO();



        dataApiService = DataApiService.getInstance();

        fetchAPI();

        model = new ViewModelProvider(this).get(DataViewModel.class);


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Intent getIntent = getIntent();
//                String title = getIntent.getStringExtra("del_title");
//                String desc = getIntent.getStringExtra("del_desc");
//                String timeStamp = getIntent.getStringExtra("del_timeStamp");
//                String lat = getIntent.getStringExtra("del_lat");
//                String lng = getIntent.getStringExtra("del_lng");
//                String addr = getIntent.getStringExtra("del_addr");
//                String e = getIntent.getStringExtra("del_e");
//                String zip = getIntent.getStringExtra("del_zip");
//                if (getIntent != null) {
//                    model.delete(new Data(title, desc, timeStamp, lat, lng, addr, e, zip));
//                }
//            }
//        });

//        model.getAllDatas().observe(this, new Observer<List<Data>>() {
//            @Override
//            public void onChanged(List<Data> data) {
//                listData.addAll(data);
////                dataAdapter.setDatas(data);
//                dataAdapter = new DataAdapter(MainActivity.this, listData);
//                binding.rvData.setAdapter(dataAdapter);
//            }
//        });

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("delete"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
//            String title = intent.getStringExtra("del_title");
//            String desc = intent.getStringExtra("del_desc");
//            String timeStamp = intent.getStringExtra("del_timeStamp");
//            String lat = intent.getStringExtra("del_lat");
//            String lng = intent.getStringExtra("del_lng");
//            String addr = intent.getStringExtra("del_addr");
//            String e = intent.getStringExtra("del_e");
//            String zip = intent.getStringExtra("del_zip");
            int position = intent.getIntExtra("position", 0);
            if (intent != null) {
                model.delete(listData.get(position));
            }
        }
    };

    public void fetchAPI() {
        Call<List<Data>> call = dataApiService.getDatas();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Data> listTmp = response.body();
                listData.addAll(listTmp);
                dataAdapter = new DataAdapter(MainActivity.this, listTmp);
                binding.rvData.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // in this on create options menu we are calling
        // a menu inflater and inflating our menu file.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        // on below line we are getting our menu item as search view item
//        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        // on below line we are creating a variable for our search view.
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        // on below line we are setting on query text listener for our search view.


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on query submit we are clearing the focus for our search view.
                dataAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // on changing the text in our search view we are calling
                // a filter method to filter our array list.
                dataAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void search(String query) {
        String searchQuery = "%$query%";


    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable("d", (Serializable) listData);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            List<Data> data = (List<Data>) savedInstanceState.getSerializable("d");
//            dataAdapter = new DataAdapter(MainActivity.this, data);
//            binding.rvData.setAdapter(dataAdapter);
//        }
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}