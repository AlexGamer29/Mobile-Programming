package com.midterm.minhduc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.midterm.minhduc.databinding.ActivityMainBinding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvData.setLayoutManager(new GridLayoutManager(this, 1));
        listData = new ArrayList<>();

        dataApiService = DataApiService.getInstance();
        fetchApi();

        DataViewModel model = new ViewModelProvider(this).get(DataViewModel.class);

        model.getUsers().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                listData.addAll(data);
                dataAdapter.setDatas(data);
                binding.rvData.setAdapter(dataAdapter);
            }
        });
    }

    public void fetchApi() {
        Call<List<Data>> call = dataApiService.getDatas();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Data> listData = response.body();
                dataAdapter = new DataAdapter(MainActivity.this, listData);
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

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable("d", listData);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            listData = (List<Data>) savedInstanceState.getSerializable("d");
//            dataAdapter = new ArrayAdapter<>(this, R.layout.data_item, listData);
//            binding.rvData.setAdapter(dataAdapter);
//        }
//        super.onRestoreInstanceState(savedInstanceState);
//    }
}