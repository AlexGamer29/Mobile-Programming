package com.midterm.nguyenhuuminhduc.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.midterm.nguyenhuuminhduc.Adapter.DataAdapter;
import com.midterm.nguyenhuuminhduc.Api.AppDatabase;
import com.midterm.nguyenhuuminhduc.Api.DataApi;
import com.midterm.nguyenhuuminhduc.Api.DataApiService;
import com.midterm.nguyenhuuminhduc.Api.DataDao;
import com.midterm.nguyenhuuminhduc.Model.Data;
import com.midterm.nguyenhuuminhduc.R;
import com.midterm.nguyenhuuminhduc.ViewModel.DataViewModel;
import com.midterm.nguyenhuuminhduc.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private List<Data> listData;
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
        dataDao = appDatabase.dataDao();

        dataApiService = DataApiService.getInstance();

        fetchAPI();
    }

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

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
}